package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;

import java.lang.reflect.InvocationHandler;
import java.util.UUID;

/**
 * Type of InvocationHandler used to communicate over sockets with a remote endpoint.
 *
 * @author Florian J. Kleine-Vorholt
 */
public interface SocketInvocationHandler extends InvocationHandler {

    /**
     * Gets the {@code ConnectionDetails} of the remote the InvocationHandler will talk to.
     *
     * @return the connection details of the remote
     */
    ConnectionDetails getRemoteConnectionDetails();

    /**
     * Gets the {@code UUID} of the domain object that is controlled using this {@link InvocationHandler}
     *
     * @return  the UUID.
     */
    UUID getUUID();
}