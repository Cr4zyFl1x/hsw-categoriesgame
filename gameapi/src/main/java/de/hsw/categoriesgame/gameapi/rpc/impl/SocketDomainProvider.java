package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.DomainProvider;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyException;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.exception.DomainInvocationException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class SocketDomainProvider implements DomainProvider {

    /**
     * The socket used for communication
     */
    private final Socket socket;

    /**
     * The registry of saved domains
     */
    private final RemoteServer localServer;

    /**
     * Actual domain
     */
    private Object domain;



    /**
     *
     * @param socket
     * @param localServer
     * @param domain
     */
    public SocketDomainProvider(final Socket socket,
                                final RemoteServer localServer,
                                final Object domain)
    {
        this.localServer = localServer;
        this.socket = socket;

        if (domain != null && !localServer.getDomainRegistry().contains(domain)) {
            localServer.getDomainRegistry().save(domain);
        }
        this.domain = domain;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDomain() {
        return domain;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void run()
    {
        try (final ObjectInputStream   in = new ObjectInputStream(socket.getInputStream());
             final ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            /*
             * RECEIVE-PART
             */

            // Read invocation information
            final String methodName     = (String) in.readObject();
            final Class<?>[] paramTypes = (Class<?>[]) in.readObject();
            final Object[] arguments    = (Object[]) in.readObject();

            // Determine Class & Method
            final UUID domainUUID     = (UUID) in.readObject();
            if (domainUUID != null && localServer.getDomainRegistry().exists(domainUUID)) {
                this.domain = localServer.getDomainRegistry().get(domainUUID);
            }

            // If domain was not found and no default is set -> null
            if (getDomain() == null) {
                throw new DomainInvocationException("No default domain was set and the client did not request a valid domain!");
            }

            // Acquire Domain class and respective method
            final Class<?> invocationClass = this.domain.getClass();
            final Method method = invocationClass.getMethod(methodName, paramTypes);

            // Deserialize Arguments
            ProxyDataSerializer serializer = new ProxySerializer(
                    new ConnectionDetails(socket.getInetAddress().getHostName()),
                    localServer,
                    method);
            final Object[] deserializedArguments = serializer.deserializeArguments(arguments);


            // Invoke Method
            Object result;
            try {
                result = method.invoke(this.domain, deserializedArguments);
            } catch (InvocationTargetException e) {
                final Throwable targetException = e.getTargetException();
                result = new ProxyException(
                        targetException.getClass(),
                        targetException.getCause(),
                        targetException.getMessage());
            }


            /*
             * SEND-PART
             */

            serializer = new ProxySerializer(
                    null,
                    localServer,
                    method);

            // Serialize return value into a serializable format
            final Object sendObj = serializer.serializeReturnValue(result, method);

            // Push into pipe
            out.writeObject(sendObj);
            out.flush();

        } catch (ClassNotFoundException | IOException | NoSuchMethodException | IllegalAccessException e) {
            // TODO: what to do?
            throw new RuntimeException(e);
        }
    }
}