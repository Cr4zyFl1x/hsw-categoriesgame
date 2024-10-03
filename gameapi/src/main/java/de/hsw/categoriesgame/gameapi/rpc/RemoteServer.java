package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;

/**
 * RemoteServer used to provide local domain objects over the network
 *
 * @author Florian J. Kleine-Vorholt
 */
public interface RemoteServer {

    /**
     * Starts the instance of the server.
     *
     * @throws RuntimeException if an error occurs during the start of the server
     */
    void start() throws RuntimeException;

    /**
     * Gets the default domain object of the server.
     * <p>
     *     This object is used if clients do not provide a reference to a specific domain stored in the registry.
     * </p>
     *
     * @return the default domain object. If no default object is set {@code null} is returned.
     */
    Object getDefaultDomain();

    /**
     * Gets the port of the server.
     * <p>
     *     This can be {@code 0} if the server has not been started yet.
     *     After the server is started a port will be allocated. Then the method will return the real port.
     * </p>
     *
     * @return the port of the server
     */
    int getPort();

    /**
     * Checks if the server is running.
     *
     * @return true, if the server is running.
     */
    boolean isRunning();

    /**
     * Get the {@code Registry} of the managed objects by the server
     *
     * @return the registry
     */
    DomainRegistry getDomainRegistry();
}