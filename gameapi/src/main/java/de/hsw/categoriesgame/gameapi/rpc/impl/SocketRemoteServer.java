package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.exception.RemoteServerException;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.SecureRandom;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class SocketRemoteServer implements RemoteServer {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(SocketRemoteServer.class);

    /**
     * Port the server should listen to
     */
    private final ConnectionDetails listeningDetails;

    /**
     * Registry used to save domains
     */
    private final DomainRegistry registry;

    /**
     * The default domain that is used if the client does not request a specific domain
     */
    private final Object defaultDomain;


    private Thread serverThread;



    public SocketRemoteServer(final ConnectionDetails listeningDetails,
                              final DomainRegistry domainRegistry,
                              final Object defaultDomain)
    {
        this.listeningDetails = listeningDetails;
        this.registry = domainRegistry;
        this.defaultDomain = defaultDomain;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws RuntimeException
    {
        // Check if server is already running
        if (isRunning()) {
            log.error("Server is already running in thread {}", serverThread.getName());
            throw new RemoteServerException("Server is already running!");
        }

        // Otherwise start server in a new thread
        this.serverThread = new Thread(() -> {
            log.info("Starting server on port {} ...", getPort());
            try (final ServerSocket serverSocket = new ServerSocket(getPort())) {
                log.info("The server has been started successfully!");

                while (true) {
                    final Socket socket = serverSocket.accept();
                    log.debug("New connection from >> '{}'", socket.getRemoteSocketAddress());

                    new Thread(new SocketDomainProvider(socket, this, getDefaultDomain())).start();
                }

            } catch (final IOException e) {
                log.error("Unable to start Server!", e);
                throw new RuntimeException(e);
            }
        }, "SRS-" + new SecureRandom().nextInt(1024));
        this.serverThread.start();
    }


    @Override
    public Object getDefaultDomain() {
        return this.defaultDomain;
    }

    @Override
    public int getPort() {
        return this.listeningDetails.getPort();
    }

    @Override
    public boolean isRunning()
    {
        return serverThread != null && serverThread.isAlive();
    }

    @Override
    public ConnectionDetails getConnectionDetails() {
        return this.listeningDetails;
    }

    @Override
    public DomainRegistry getDomainRegistry() {
        return registry;
    }
}
