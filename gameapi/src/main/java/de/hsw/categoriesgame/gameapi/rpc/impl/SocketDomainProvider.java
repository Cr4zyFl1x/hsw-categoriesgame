package de.hsw.categoriesgame.gameapi.rpc.impl;

import de.hsw.categoriesgame.gameapi.rpc.DomainProvider;
import de.hsw.categoriesgame.gameapi.rpc.ProxyDataSerializer;
import de.hsw.categoriesgame.gameapi.rpc.ProxyException;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;

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
    private final DomainRegistry registry;

    /**
     * Actual domain
     */
    private Object domain;



    /**
     *
     * @param socket
     * @param registry
     * @param domain
     */
    public SocketDomainProvider(final Socket socket,
                                final DomainRegistry registry,
                                final Object domain)
    {
        this.registry = registry;
        this.socket = socket;

        if (domain == null) {
            throw new IllegalArgumentException("Domain cannot be null");
        }

        if (!registry.contains(domain)) {
            registry.save(domain);
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
            if (domainUUID != null && registry.exists(domainUUID)) {
                this.domain = registry.get(domainUUID);
            }
            final Class<?> invocationClass = this.domain.getClass();
            final Method method = invocationClass.getMethod(methodName, paramTypes);

            // Invoke Method
            Object result;
            try {
                result = method.invoke(this.domain, arguments);
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

            final ProxyDataSerializer serializer = new ProxySerializer(null, registry, method);
            final Object sendObj = serializer.serializeReturnValue(result);

            // Push into pipe
            out.writeObject(sendObj);
            out.flush();

        } catch (ClassNotFoundException | IOException | NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}