package de.hsw.categoriesgame.gameapi.rpc;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface RemoteServer {

    void start() throws RuntimeException;

    Object getDefaultDomain();

    int getPort();
}
