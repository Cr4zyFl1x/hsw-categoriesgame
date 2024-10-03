package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyData;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyFactoryRegistry;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.lang.reflect.Proxy;
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
    private final ConnectionDetails remoteConnection;

    /**
     * The local (client) server to manage domain objects on client side
     */
    private final RemoteServer localConnection;


    /**
     * Creates a new {@code RememberableProxyFactory}.
     */
    public RememberableProxyFactory(final ConnectionDetails remoteConnection, RemoteServer localConnection)
    {
        if (remoteConnection == null) {
            throw new IllegalArgumentException("Connection details cannot be null");
        }
        this.remoteConnection = remoteConnection;
        this.localConnection = localConnection;

        // ClientServer running?
        if (!localConnection.isRunning())
            throw new RuntimeException("Local Server is not running!");

        this.proxyRegistry = new ProxyRegistry();

        // ADD to Registry of Factories
        ProxyFactoryRegistry.getRegistry().save(remoteConnection, this);
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
                new DynamicSocketInvocationHandler(
                        remoteConnection,
                        localConnection,
                        domainUUID == null ? null : UUID.fromString(domainUUID))
        );

        // Save proxy to registry
        if (domainUUID != null) {
            proxyRegistry.save(
                    UUID.fromString(domainUUID),
                    new ProxyData(remoteConnection, UUID.fromString(domainUUID), clazz, proxy));
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

    @Override
    public ConnectionDetails getEndpointConnectionDetails() {
        return remoteConnection;
    }
}
