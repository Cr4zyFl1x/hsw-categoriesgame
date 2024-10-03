package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;

/**
 * Global {@link ProxyFactoryRegistry} to store multiple {@link ProxyFactory} objects
 *
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyFactoryRegistry extends RegistryBase<ConnectionDetails, ProxyFactory> {

    /**
     * The registry
     */
    private static final ProxyFactoryRegistry REGISTRY;
    static {
        REGISTRY = new ProxyFactoryRegistry();
    }



    /**
     * Creates a new ProxyFactoryRegistry
     */
    private ProxyFactoryRegistry() {}



    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionDetails generateKey() {
        throw new UnsupportedOperationException("This registry does not support auto generation of keys!");
    }


    /**
     * Gets the global Registry
     */
    public static ProxyFactoryRegistry getRegistry()
    {
        return REGISTRY;
    }


    /**
     * Gets an already existing proxy factory or creates it if necessary.
     *
     * @param remote        the remote endpoint the proxy should talk to
     * @param localServer   the local {@link RemoteServer} to provide handleable Proxies on the remote side
     *
     * @return the newly created or existing {@code ProxyFactory}.
     */
    public static ProxyFactory getFactoryOrCreate(final ConnectionDetails remote, final RemoteServer localServer)
    {
        if (remote == null) {
            throw new IllegalArgumentException("key cannot be null");
        }

        if (getRegistry().exists(remote)) {

            return getRegistry().get(remote);
        }

        return new RememberableProxyFactory(remote, localServer);
    }
}