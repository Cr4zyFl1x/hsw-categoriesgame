package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
    private final int port;

    /**
     * Registry used to save domains
     */
    private final DomainRegistry registry;

    /**
     * The default domain that is used if the client does not request a specific domain
     */
    private final Object defaultDomain;



    public SocketRemoteServer(final int port,
                              final DomainRegistry domainRegistry,
                              final Object defaultDomain)
    {
        this.port = port;
        this.registry = domainRegistry;
        this.defaultDomain = defaultDomain;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public void start() throws RuntimeException
    {
        log.info("Starting server on port {}", getPort());
        try (final ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("The server has been started successfully!");

            while (true) {
                final Socket socket = serverSocket.accept();
                log.debug("New connection from >> '{}'", socket.getRemoteSocketAddress());

                new Thread(new SocketDomainProvider(socket, registry, getDefaultDomain())).start();
            }

        } catch (final IOException e) {
            log.error("Unable to start Server!", e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public Object getDefaultDomain() {
        return this.defaultDomain;
    }

    @Override
    public int getPort() {
        return this.port;
    }
}
