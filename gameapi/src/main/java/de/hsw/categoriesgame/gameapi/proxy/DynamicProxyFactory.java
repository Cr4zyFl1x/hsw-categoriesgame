package de.hsw.categoriesgame.gameapi.proxy;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface DynamicProxyFactory {

    public <T> T getProxy(Class<T> clazz);
}