package de.hsw.categoriesgame.gameapi.rpc.impl;

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
 * {@link RemoteServer} that communicates over Sockets
 *
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
    private Integer port;

    /**
     * Registry used to save domains
     */
    private final DomainRegistry registry;

    /**
     * The default domain that is used if the client does not request a specific domain
     */
    private final Object defaultDomain;

    /**
     * The thread the server is running in.
     */
    private Thread serverThread;


    /**
     * Creates a new SocketRemoteServer that will be exposed on a specific port and provides a default domain.    *
     *
     * @param port              the port on which the server will listen
     * @param domainRegistry    the domain registry where manageable objects are saved
     * @param defaultDomain     the default domain
     */
    public SocketRemoteServer(final int port,
                              final DomainRegistry domainRegistry,
                              final Object defaultDomain)
    {
        if (domainRegistry == null) {
            throw new IllegalArgumentException("DomainRegistry cannot be null");
        }

        this.port = port;
        this.registry = domainRegistry;
        this.defaultDomain = defaultDomain;
    }

    /**
     * Creates a new SocketRemoteServer that will be exposed on a random free port and provides a default domain.
     *
     * @param domainRegistry    the domain registry where manageable objects are saved
     * @param defaultDomain     the default domain
     */
    public SocketRemoteServer(final DomainRegistry domainRegistry,
                              final Object defaultDomain)
    {
        if (domainRegistry == null) {
            throw new IllegalArgumentException("DomainRegistry cannot be null");
        }

        this.port = null;
        this.registry = domainRegistry;
        this.defaultDomain = defaultDomain;
    }


    /**
     * Creates a new SocketRemoteServer that will be exposed on a random free port.
     *
     * @param domainRegistry    the domain registry where manageable objects are saved
     */
    public SocketRemoteServer(final DomainRegistry domainRegistry)
    {
        if (domainRegistry == null) {
            throw new IllegalArgumentException("DomainRegistry cannot be null");
        }

        this.port = null;
        this.registry = domainRegistry;
        this.defaultDomain = null;
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
            log.info("Starting server ...");
            try (final ServerSocket serverSocket = new ServerSocket(getPort())) {

                if (this.port == null) {
                    this.port = serverSocket.getLocalPort();
                }
                log.info("The server has been started successfully o port {}!", getPort());

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


    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDefaultDomain() {
        return this.defaultDomain;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getPort() {
        if (this.port == null) {
            return 0;
        }
        return this.port;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRunning()
    {
        return serverThread != null && serverThread.isAlive();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public DomainRegistry getDomainRegistry() {
        return registry;
    }
}