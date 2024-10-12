package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.*;
import de.hsw.categoriesgame.gameapi.rpc.exception.SerializationException;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyFactoryRegistry;
import de.hsw.categoriesgame.gameapi.util.ReflectionUtil;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Serializer to serialize/deserialize non serializable date to push it through a {@link java.io.ObjectOutputStream}
 *
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxySerializer implements ProxyDataSerializer {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(ProxySerializer.class);

    /**
     * The local server instance
     */
    private final RemoteServer localServer;

    /**
     * The method the serializer serializes data for
     */
    @Getter
    private final Method method;

    /**
     * Remote {@link ConnectionDetails} used for creating {@code ProxyData} objects that are used on the other
     * side for creating proxies to manage local domain objects
     */
    private final ConnectionDetails remoteConnectionDetails;


    /**
     * Creates a new ProxySerializer
     *
     * @param remoteConnectionDetails   remote connection details for serializing local domains to ProxyData
     * @param localServer               the local server for adding domains to the registry of manageable objects
     * @param method                    the current method
     */
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
    public Object[] serializeArguments(Object[] args)
    {
        if (args == null || args.length == 0) {
            return args;
        }

        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];

            // NULL
            if (arg == null) {
                continue;
            }

            // IS PROXY
            if (arg instanceof Proxy) {
                args[i] = serializeProxyToDomainData((Proxy) arg);
            }

            // LIST
            if (arg instanceof List<?>) {
                args[i] = serializeList((List<Object>) arg, ReflectionUtil.getParameterGenericType(getMethod(), i));
            }

            // MAP
            if (arg instanceof Map<?, ?>) {
                throw new UnsupportedOperationException("MAP is not implemented yet");
            }

            // Shall create a Proxy for
            if (!(arg instanceof Serializable)) {
                args[i] = serializeDomainToProxyData(arg, ReflectionUtil.getMethodParameterType(getMethod(), i));
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

            if (arg instanceof DomainData) {
                args[i] = deserializeDomainDataToDomain((DomainData) arg);
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
    public Object serializeReturnValue(Object returnValue)
    {
        // NULL
        if (returnValue == null) {
            return null;
        }

        // IS PROXY
        if (returnValue instanceof Proxy) {
            return serializeProxyToDomainData((Proxy) returnValue);
        }

        // LIST
        if (returnValue instanceof List<?>) {
            return serializeList((List<Object>) returnValue, ReflectionUtil.getGenericReturnType(getMethod()));
        }

        // MAP
        if (returnValue instanceof Map<?, ?>) {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        // Non-Serializable as Proxy
        if (!(returnValue instanceof Serializable)) {
            return serializeDomainToProxyData(returnValue, ReflectionUtil.getMethodReturnType(getMethod()));
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

        if (object instanceof DomainData) {
            return deserializeDomainDataToDomain((DomainData) object);
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



    private List<Object> serializeList(final List<Object> list, Class<?> listClass)
    {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return list;
        }

        final List<Object> returnList   = new ArrayList<>();

        // To domain data
        if (list.get(0) instanceof Proxy) {
            for (final Object o : list) {
                final DomainData domainData = serializeProxyToDomainData((Proxy) o);
                returnList.add(domainData);
            }
            return returnList;
        }

        if (list.get(0) instanceof Serializable) {
            return list;
        }

        // To ProxyData
        for (final Object object : list) {
            final ProxyData proxyData = serializeDomainToProxyData(object, listClass);
            returnList.add(proxyData);
        }

        return returnList;
    }



    private List<Object> deserializeList(final List<Object> list)
    {
        if (list.isEmpty() || (!(list.get(0) instanceof ProxyData)
                && (!(list.get(0) instanceof DomainData))
                && (list.get(0) instanceof Serializable)))
        {
            return list;
        }

        final List<Object> retList = new ArrayList<>(list.size());

        // DomainData -> Domain
        if (list.get(0) instanceof DomainData) {
            for (final Object o : list) {
                final Object ob = deserializeDomainDataToDomain((DomainData) o);
                retList.add(ob);
            }
            return retList;
        }

        // ProxyData -> Proxy
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



    private ProxyData serializeDomainToProxyData(final Object domain, Class<?> clazz)
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



    private Object deserializeProxyDataToProxy(final ProxyData proxyData)
    {
        // Is localDomain?
        proxyData.setConnectionDetails(new ConnectionDetails(
                remoteConnectionDetails.getHost(),
                proxyData.getConnectionDetails().getPort()));

        return ProxyFactoryRegistry.getFactoryOrCreate(proxyData.getConnectionDetails(),
                localServer).createProxy(proxyData);
    }


    private DomainData serializeProxyToDomainData(final Proxy proxy)
    {
        final SocketInvocationHandler socketInvocationHandler = (SocketInvocationHandler) Proxy.getInvocationHandler(proxy);

        final Class<?>[] prIf = proxy.getClass().getInterfaces();
        if (prIf.length == 0) {
            throw new SerializationException("Cannot find interface!");
        }

        final Class<?> interfaceClass = prIf[0];        // Should be the first
        final UUID domainUUID = socketInvocationHandler.getUUID();

        return new DomainData(interfaceClass, domainUUID);
    }


    private Object deserializeDomainDataToDomain(final DomainData domainData)
    {
        if (!localServer.getDomainRegistry().exists(domainData.getUuid()))
            throw new SerializationException("No respective Domain found for UUID: " + domainData.getUuid());

        return localServer.getDomainRegistry().get(domainData.getUuid());
    }
}