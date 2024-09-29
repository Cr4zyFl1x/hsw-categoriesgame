package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyFactory {

    /**
     *
     * @param clazz
     * @return
     * @param <T>
     */
    default <T> T createProxy(final Class<T> clazz) {
        return createProxy(clazz, null);
    }

    /**
     *
     * @param proxyData
     * @return
     */
    default Proxy createProxy(final ProxyData proxyData)
    {
        try {
            return proxyData.getProxy();
        } catch (IllegalStateException e) {
            return (Proxy) createProxy(proxyData.getClazz(), proxyData.getDomainUUID().toString());
        }
    }

    /**
     *
     * @param clazz
     * @param domainUUID
     * @return
     * @param <T>
     */
    <T> T createProxy(final Class<T> clazz, final String domainUUID);


    ProxyRegistry getProxyRegistry();
}