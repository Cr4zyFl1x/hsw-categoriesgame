package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyException;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.SocketInvocationHandler;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class DynamicSocketInvocationHandler implements SocketInvocationHandler {

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
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (method.getName().equals("equals") && (args[0] instanceof Proxy)) {
            return args[0].hashCode() == proxy.hashCode();
        }
        if (method.getName().equals("hashCode")) {
            return System.identityHashCode(proxy);
        }
        if (method.getName().equals("toString")) {
            return proxy.getClass().toString();
        }

        try (final Socket sock = new Socket(remoteConnectionDetails.getHost(), remoteConnectionDetails.getPort())) {

            // SERIALIZER
            ProxyDataSerializer serializer = new ProxySerializer(null,
                    localServer,
                    method);

            sock.setTcpNoDelay(true);

            try (final ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()))) {
                // Send Method
                sendMethodInvocationInformation(out, method);

                // Send Arguments
                out.writeObject(serializer.serializeArguments(args, method));

                    // Send InvocationDomain-Identifier
                out.writeObject(domainUUID);

                out.flush();
            };

            try (final ObjectInputStream  in  = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()))) {
                final Object result = in.readObject();

                // If is exception -> throw exception
                if (result instanceof ProxyException) {
                    throw ((ProxyException) result).buildException();
                }

                serializer = new ProxySerializer(
                    new ConnectionDetails(sock.getInetAddress().getHostName()),
                localServer,
                method);

                // Deserialize result
                final Object ret = serializer.deserializeReturnValue(result);

                // Return
                return ret;
            }
        }
    }


    private void sendMethodInvocationInformation(final ObjectOutputStream out, final Method method) throws IOException
    {
        out.writeObject(method.getName());
        out.writeObject(method.getParameterTypes());
    }


}
