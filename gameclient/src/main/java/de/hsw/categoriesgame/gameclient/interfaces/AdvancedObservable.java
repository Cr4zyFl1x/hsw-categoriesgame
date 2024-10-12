package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface AdvancedObservable<T> {

    void register(T category, AdvancedObserver observer);

    void sendNotification(T... category);
}