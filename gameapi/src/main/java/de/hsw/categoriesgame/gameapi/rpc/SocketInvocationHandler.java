package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;

import java.lang.reflect.InvocationHandler;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface SocketInvocationHandler extends InvocationHandler {

    ConnectionDetails getRemoteConnectionDetails();
    ConnectionDetails getLocalConnectionDetails();
}