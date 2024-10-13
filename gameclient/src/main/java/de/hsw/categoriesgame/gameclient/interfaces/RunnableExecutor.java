package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface RunnableExecutor<T> {

    void register(String category, Runnable runnable);

    void callRunnable(String category);
}
