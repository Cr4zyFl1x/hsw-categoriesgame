package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyData;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyFactoryRegistry;
import de.hsw.categoriesgame.gameapi.util.ReflectionUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxySerializer implements ProxyDataSerializer {


    private final RemoteServer localServer;

    private final Method method;

    private final ConnectionDetails remoteConnectionDetails;


    public ProxySerializer (final ConnectionDetails remoteConnectionDetails,
                            final RemoteServer localServer,
                            final Method method) {
        this.method = method;
        this.remoteConnectionDetails = remoteConnectionDetails;
        this.localServer = localServer;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object[] serializeArguments(Object[] args, final Method method) {

        if (args == null || args.length == 0) {
            return args;
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            // NULL
            if (arg == null) {
                continue;
            }

            // LIST
            if (arg instanceof List<?>) {
                args[i] = serializeList((List<Object>) arg, ReflectionUtil.getParameterGenericType(method, i));
            }

            // MAP
            if (arg instanceof Map<?, ?>) {
                throw new UnsupportedOperationException("MAP is not implemented yet");
            }

            // Shall create a Proxy for
            if (!(arg instanceof Serializable)) {
                args[i] = serializeDomainToProxy(arg, ReflectionUtil.getMethodParameterType(method, i));
            }

            // Otherwise must be serializable --> do nothing
        }
        return args;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object[] deserializeArguments(Object[] args)
    {
        if (args == null || args.length == 0) {
            return args;
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            if (arg == null) {
                continue;
            }

            if (arg instanceof List<?>) {
                args[i] = deserializeList((List<Object>) arg);
            }

            if (arg instanceof Map<?, ?>) {
                throw new UnsupportedOperationException("MAP is not implemented yet");
            }

            if (arg instanceof ProxyData) {
                args[i] = deserializeProxyDataToProxy((ProxyData) arg);
            }

            // Else -> Should be serializable
        }

        return args;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object serializeReturnValue(Object returnValue, Method method)
    {
        // NULL
        if (returnValue == null) {
            return null;
        }

        // LIST
        if (returnValue instanceof List<?>) {
            return serializeList((List<Object>) returnValue, ReflectionUtil.getGenericReturnType(method));
        }

        // MAP
        if (returnValue instanceof Map<?, ?>) {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        // Non-Serializable as Proxy
        if (!(returnValue instanceof Serializable)) {
            return serializeDomainToProxy(returnValue, ReflectionUtil.getMethodReturnType(method));
        }

        // Should be serializable
        return returnValue;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object deserializeReturnValue(Object object)
    {
        // NULL
        if (object == null) {
            return null;
        }

        // LIST
        if (object instanceof List<?>) {
            return deserializeList((List<Object>) object);
        }

        // MAP
        if (object instanceof Map<?, ?>) {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        // ProxyData --> Proxy
        if (object instanceof ProxyData) {
            return deserializeProxyDataToProxy((ProxyData) object);
        }

        return object;
    }


    /////////////////////////////////////////////////
    /////////////////////////////////////////////////


    /**
     * Converts a list of domain objects into a List of {@code ProxyData} objects that can be serialized and transferred.
     * These {@code ProxyData} objects can then be used to create proxies to manage these domain objects.
     *
     * @param list  list containing the domain objects
     * @return
     */
    private List<Object> serializeList(final List<Object> list, Class<?> listClass)
    {
        if (list == null) {
            return null;
        }

        if (list.isEmpty() || ((list.get(0) instanceof Serializable))) {
            return list;
        }
        final List<Object> returnList   = new ArrayList<Object>();
        for (final Object object : list) {
            if (localServer.getDomainRegistry().contains(object)) {
                final UUID domainUUID = localServer.getDomainRegistry().getKey(object);
                returnList.add(new ProxyData(new ConnectionDetails(null, localServer.getPort()), domainUUID, listClass));
                continue;
            }
            final UUID uuid = localServer.getDomainRegistry().save(object);
            returnList.add(new ProxyData(new ConnectionDetails(null, localServer.getPort()), uuid, listClass));
        }

        return returnList;
    }

    /**
     *
     * @param list
     * @return
     */
    private List<Object> deserializeList(final List<Object> list)
    {
        if (list.isEmpty() || (!(list.get(0) instanceof ProxyData) && (list.get(0) instanceof Serializable))) {
            return list;
        }

        final List<Object> retList = new ArrayList<>(list.size());
        for (final Object object : list) {
            final ProxyData proxyData = (ProxyData) object;

            proxyData.setConnectionDetails(
                    new ConnectionDetails(remoteConnectionDetails.getHost(), proxyData.getConnectionDetails().getPort()));

            final ProxyFactory factory = ProxyFactoryRegistry.getFactoryOrCreate(proxyData.getConnectionDetails(),
                    localServer);
            retList.add(factory.createProxy((ProxyData) object));
        }
        return retList;
    }

    /**
     *
     * @param domain
     * @return
     */
    private ProxyData serializeDomainToProxy(final Object domain, Class<?> clazz)
    {
        if (localServer.getDomainRegistry().contains(domain)) {
            return new ProxyData(
                    new ConnectionDetails(null, localServer.getPort()),
                    localServer.getDomainRegistry().getKey(domain),
                    clazz);
        }

        final UUID domainUUID = localServer.getDomainRegistry().save(domain);
        return new ProxyData(new ConnectionDetails(null, localServer.getPort()), domainUUID, clazz);
    }

    /**
     *
     * @param proxyData
     * @return
     */
    private Object deserializeProxyDataToProxy(final ProxyData proxyData)
    {
        proxyData.setConnectionDetails(new ConnectionDetails(
                remoteConnectionDetails.getHost(),
                proxyData.getConnectionDetails().getPort()));

        return ProxyFactoryRegistry.getFactoryOrCreate(proxyData.getConnectionDetails(),
                localServer).createProxy(proxyData);
    }
}
