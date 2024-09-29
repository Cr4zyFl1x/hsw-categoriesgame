package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface RemoteServer {

    void start() throws RuntimeException;

    Object getDefaultDomain();

    int getPort();

    boolean isRunning();

    ConnectionDetails getConnectionDetails();

    DomainRegistry getDomainRegistry();
}
