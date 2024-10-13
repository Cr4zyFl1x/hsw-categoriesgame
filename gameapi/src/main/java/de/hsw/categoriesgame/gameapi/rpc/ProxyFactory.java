package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.lang.reflect.Proxy;

/**
 * The ProxyFactory is used to communicate with a {@link RemoteServer} to start remote procedure calls.
 *
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyFactory {

    /**
     * Creates a Proxy for the default domain on the server side.
     *
     * @param clazz the interface of the default domain
     *
     * @return  a proxy for communication with the default domain on the RemoteServer
     *
     * @param <T> the type/interface of the default domain
     */
    default <T> T createProxy(final Class<T> clazz)
    {
        return createProxy(clazz, null);
    }

    /**
     * Creates a proxy by given {@link ProxyData}
     *
     * @param proxyData the ProxyData with information
     *
     * @return a proxy for communication with the domain specified in the ProxyData
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
     * Creates a specific proxy to manage a specific domain on the remote endpoint
     *
     * @param clazz         the class of the domain object (interface)
     * @param domainUUID    the UUID of the remote domain
     *
     * @return              a proxy for the desired remote domain
     * @param <T>           the type of the domain
     */
    <T> T createProxy(final Class<T> clazz, final String domainUUID);

    /**
     * Gets the Registry for this ProxyFactory containing the created proxies
     *
     * @return the ProxyRegistry
     */
    ProxyRegistry getProxyRegistry();

    /**
     * Gets the ConnectionDetails of the remote endpoint where the Proxies will connect to.
     *
     * @return the ConnectionDetails of the remote endpoint
     */
    ConnectionDetails getEndpointConnectionDetails();
}