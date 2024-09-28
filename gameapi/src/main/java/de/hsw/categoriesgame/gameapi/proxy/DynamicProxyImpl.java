package de.hsw.categoriesgame.gameapi.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class DynamicProxyImpl implements InvocationHandler {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(DynamicProxyImpl.class);

    /**
     * COM socket
     */
    private final Socket socket;


    private Class<?> clazz;

    private ObjectOutputStream send;
    private ObjectInputStream receive;

    public DynamicProxyImpl(final Socket socket, final Class<?> clazz) {

        this.socket = socket;

        try {
            this.send = new ObjectOutputStream(socket.getOutputStream());
            this.receive = new ObjectInputStream(socket.getInputStream());
            this.clazz = clazz;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args)
    {

        send.println(method.getName());
        send.println();
        send.println(Arrays.stream(args).map(Object::toString).collect(Collectors.joining("|")));
        send.flush();

        return null;
    }
}
