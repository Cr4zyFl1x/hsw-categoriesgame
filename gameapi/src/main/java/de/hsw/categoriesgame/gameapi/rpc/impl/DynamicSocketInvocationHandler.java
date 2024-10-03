package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.perf.RuntimeMeasurer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyException;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.SocketInvocationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class DynamicSocketInvocationHandler implements SocketInvocationHandler {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DynamicSocketInvocationHandler.class);

    /**
     * The connection details with remote information
     */
    private final ConnectionDetails remoteConnectionDetails;

    /**
     * The connection details with remote information
     */
    private final RemoteServer localServer;

    /**
     * Actual UUID of the domain object to be handled on remote
     */
    private final UUID domainUUID;



    /**
     * Creates a new {@link DynamicSocketInvocationHandler}
     *
     * @param remoteConnectionDetails   the connection details the socket will be established to.
     * @param localServer               the local server
     * @param domainUUID                the domain uuid to contact by default
     */
    public DynamicSocketInvocationHandler(final ConnectionDetails remoteConnectionDetails,
                                          final RemoteServer localServer,
                                          final UUID domainUUID)
    {
        if (remoteConnectionDetails == null || localServer == null) {
            throw new IllegalArgumentException("The connection details cannot be null!");
        }

        this.remoteConnectionDetails = remoteConnectionDetails;
        this.localServer = localServer;
        this.domainUUID = domainUUID;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionDetails getRemoteConnectionDetails()
    {
        return this.remoteConnectionDetails;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID() {
        return domainUUID;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        final RuntimeMeasurer measurer = new RuntimeMeasurer().start();

        if (method.getName().equals("equals") && (args[0] instanceof Proxy)) {
            return args[0] == proxy;
        }
        if (method.getName().equals("hashCode")) {
            return System.identityHashCode(proxy);
        }
        if (method.getName().equals("toString")) {
            return proxy.getClass().toString();
        }

        try (final Socket sock = new Socket(remoteConnectionDetails.getHost(), remoteConnectionDetails.getPort());
             final ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
             final ObjectInputStream  in  = new ObjectInputStream(sock.getInputStream())) {

            // SERIALIZER
            ProxyDataSerializer serializer = new ProxySerializer(null,
                    localServer,
                    method);

            /*
             * SEND-PART
             */

            // Send Method
            out.writeObject(method.getName());
            out.writeObject(method.getParameterTypes());

            // Send Arguments
            out.writeObject(serializer.serializeArguments(args));

            // Send InvocationDomain-Identifier
            out.writeObject(domainUUID);

            out.flush();



            /*
             * RECEIVE-PART
             */

            final Object result = in.readObject();

            // If is exception -> throw exception
            if (result instanceof ProxyException) {
                throw ((ProxyException) result).buildException();
            }

            serializer = new ProxySerializer(
                    new ConnectionDetails(sock.getInetAddress().getHostAddress()),
                    localServer,
                    method);

            // Deserialize result
            final Object ret = serializer.deserializeReturnValue(result);

            log.trace("Processing the request '{}' took {} milliseconds.",
                    method.getName() + "@" +  method.getDeclaringClass().getSimpleName(),
                    measurer.stop());

            // Return
            return ret;
        }
    }
}