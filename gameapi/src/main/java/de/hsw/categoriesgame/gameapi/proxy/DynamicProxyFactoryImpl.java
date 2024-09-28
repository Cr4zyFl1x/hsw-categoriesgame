package de.hsw.categoriesgame.gameapi.proxy;

import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class DynamicProxyFactoryImpl implements DynamicProxyFactory {

    private final Socket socket;

    public DynamicProxyFactoryImpl(final Socket socket) {
        this.socket = socket;
    }

    @Override
    public <T> T getProxy(Class<T> clazz)
    {
        @SuppressWarnings("all")
        final T proxy = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new DynamicProxyImpl(socket, clazz));
        return proxy;
    }
}