package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;

import java.lang.reflect.InvocationHandler;
import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface SocketInvocationHandler extends InvocationHandler {

    public ConnectionDetails getConnectionDetails();

    public ProxyFactory getProxyFactory();
}