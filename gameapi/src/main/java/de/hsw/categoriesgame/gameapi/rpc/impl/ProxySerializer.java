package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.rpc.*;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.RegistryBase;
import de.hsw.categoriesgame.gameapi.util.ReflectionUtil;

import java.awt.image.BufferedImageFilter;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxySerializer implements ProxyDataSerializer {

    private final ProxyFactory proxyFactory;
    private final DomainRegistry domainRegistry;
    private final Method method;


    public ProxySerializer(final ProxyFactory proxyFactory,
                           final DomainRegistry domainRegistry,
                           final Method method) {
        this.method = method;
        this.domainRegistry = domainRegistry;
        this.proxyFactory = proxyFactory;
    }



    @Override
    public Object[] serializeArguments(Object[] args) {
        return new Object[0];
    }


    @Override
    @SuppressWarnings("unchecked")
    public Object serializeReturnValue(Object returnValue)
    {
        // NULL
        if (returnValue == null) {
            return null;
        }

        // LIST
        if (returnValue instanceof List<?>) {
            return serializeList((List<Object>) returnValue);
        }

        // MAP
        if (returnValue instanceof Map<?, ?>) {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        // Non-Serializable as Proxy
        if (!(returnValue instanceof Serializable)) {
            return serializeDomainToProxy(returnValue);
        }

        // Should be serializable
        return returnValue;
    }


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


    /**
     * Converts a list of domain objects into a List of {@code ProxyData} objects that can be serialized and transferred.
     * These {@code ProxyData} objects can then be used to create proxies to manage these domain objects.
     *
     * @param list  list containing the domain objects
     * @return
     */
    private List<Object> serializeList(final List<Object> list)
    {
        if (list == null) {
            return null;
        }

        if (list.isEmpty() || ((list.get(0) instanceof Serializable))) {
            return list;
        }

        final Class<?> genericListType  = ReflectionUtil.getGenericReturnType(method);
        final List<Object> returnList   = new ArrayList<Object>();
        for (final Object object : list) {
            if (domainRegistry.contains(object)) {
                final UUID domainUUID = domainRegistry.getKey(object);
                returnList.add(new ProxyData(domainUUID, genericListType));
                continue;
            }
            final UUID uuid = domainRegistry.save(object);
            returnList.add(new ProxyData(uuid, genericListType));
        }

        return returnList;
    }

    private List<Object> deserializeList(final List<Object> list)
    {
        if (list.isEmpty() || (!(list.get(0) instanceof ProxyData) && (list.get(0) instanceof Serializable))) {
            return list;
        }

        final List<Object> retList = new ArrayList<>(list.size());
        for (final Object object : list) {
            retList.add(proxyFactory.createProxy((ProxyData) object));
        }
        return retList;
    }


    private ProxyData serializeDomainToProxy(final Object domain)
    {
        if (domainRegistry.contains(domain)) {
            return new ProxyData(domainRegistry.getKey(domain), method.getReturnType());
        }

        final UUID domainUUID = domainRegistry.save(domain);
        return new ProxyData(domainUUID, method.getReturnType());
    }

    private Object deserializeProxyDataToProxy(final ProxyData proxyData)
    {
        return proxyFactory.createProxy(proxyData);
    }
}
