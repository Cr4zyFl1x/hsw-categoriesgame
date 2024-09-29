package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyException;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.SocketInvocationHandler;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class DynamicSocketInvocationHandler implements SocketInvocationHandler {

    /**
     * The connection details with remote information
     */
    private final ConnectionDetails connectionDetails;

    /**
     * Factory to create new proxies on demand
     */
    private final ProxyFactory proxyFactory;

    /**
     * Actual UUID of the domain object to be handled on remote
     */
    private UUID domainUUID;



    public DynamicSocketInvocationHandler(final ConnectionDetails connectionDetails,
                                          final ProxyFactory proxyFactory,
                                          final UUID domainUUID)
    {
        if (connectionDetails == null || proxyFactory == null) {
            throw new IllegalArgumentException("The communication socket or proxy factory is null!");
        }

        this.connectionDetails = connectionDetails;
        this.proxyFactory = proxyFactory;
        this.domainUUID = domainUUID;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public ConnectionDetails getConnectionDetails()
    {
        return this.connectionDetails;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProxyFactory getProxyFactory()
    {
        return this.proxyFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {
        if (method.getName().equals("toString")) {
            return proxy.getClass().toString();
        }

        try (final Socket sock = new Socket(connectionDetails.getHost(), connectionDetails.getPort());
             final ObjectOutputStream out = new ObjectOutputStream(sock.getOutputStream());
             final ObjectInputStream  in  = new ObjectInputStream(sock.getInputStream())) {

            /*
             * SEND-PART
             */

            // Send Method
            sendMethodInvocationInformation(out, method);

            // Send Arguments
            out.writeObject(args);

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

            // Otherwise deserialize and return
            final ProxyDataSerializer serializer = new ProxySerializer(getProxyFactory(), null, method);
            return serializer.deserializeReturnValue(result);
        }
    }


    private void sendMethodInvocationInformation(final ObjectOutputStream out, final Method method) throws IOException
    {
        out.writeObject(method.getName());
        out.writeObject(method.getParameterTypes());
    }


}
