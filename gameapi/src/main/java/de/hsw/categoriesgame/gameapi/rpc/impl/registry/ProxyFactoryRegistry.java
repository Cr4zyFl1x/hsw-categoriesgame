package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyFactoryRegistry extends RegistryBase<ConnectionDetails, ProxyFactory> {

    private static final ProxyFactoryRegistry REGISTRY;

    static {
        REGISTRY = new ProxyFactoryRegistry();
    }

    private ProxyFactoryRegistry() {}

    @Override
    public ConnectionDetails generateKey() {
        throw new UnsupportedOperationException("This registry does not support auto generation of keys!");
    }

    public static ProxyFactoryRegistry getRegistry()
    {
        return REGISTRY;
    }

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