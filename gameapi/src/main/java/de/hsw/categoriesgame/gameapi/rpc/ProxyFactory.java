package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.lang.reflect.Proxy;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyFactory {

    default <T> T createProxy(final Class<T> clazz)
    {
        return createProxy(clazz, null);
    }

    default Proxy createProxy(final ProxyData proxyData)
    {
        try {
            return proxyData.getProxy();
        } catch (IllegalStateException e) {
            return (Proxy) createProxy(proxyData.getClazz(), proxyData.getDomainUUID().toString());
        }
    }

    <T> T createProxy(final Class<T> clazz, final String domainUUID);

    ProxyRegistry getProxyRegistry();

    ConnectionDetails getEndpointConnectionDetails();
}