package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface RunnableExecutor<T> {

    void register(T category, Runnable runnable);

    void callRunnable(T... category);
}
