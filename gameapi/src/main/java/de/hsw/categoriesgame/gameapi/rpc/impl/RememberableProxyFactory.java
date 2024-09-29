package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyData;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class RememberableProxyFactory implements ProxyFactory {

    /**
     * The registry of created proxies to enable reuse of them
     */
    private final ProxyRegistry proxyRegistry;

    /**
     * The (resolvable) hostname of the server to connect to
     */
    private final ConnectionDetails connectionDetails;



    /**
     * Creates a new {@code RememberableProxyFactory}.
     */
    public RememberableProxyFactory(final ConnectionDetails connectionDetails)
    {
        if (connectionDetails == null) {
            throw new IllegalArgumentException("Connection details cannot be null");
        }
        this.connectionDetails = connectionDetails;

        this.proxyRegistry = new ProxyRegistry();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T createProxy(Class<T> clazz, String domainUUID)
    {
        // Use already existing proxy instead of creating new
        if (domainUUID != null && proxyRegistry.exists(UUID.fromString(domainUUID))
                && proxyRegistry.get(UUID.fromString(domainUUID)).getClazz().equals(clazz)) {
            return (T) proxyRegistry.get(UUID.fromString(domainUUID)).getProxy();
        }

        // Create proxy if not exists
        final Proxy proxy = (Proxy) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                new DynamicSocketInvocationHandler(connectionDetails, this, domainUUID == null ? null : UUID.fromString(domainUUID))
        );

        // Save proxy to registry
        if (domainUUID != null) {
            proxyRegistry.save(new ProxyData(UUID.fromString(domainUUID), clazz, proxy));
        }

        // Return proxy
        return (T) proxy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProxyRegistry getProxyRegistry()
    {
        return proxyRegistry;
    }
}
