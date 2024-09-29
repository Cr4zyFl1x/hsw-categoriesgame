package de.hsw.categoriesgame.gameapi.rpc;

import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface Registry<K, V> {

    V get(final K uuid) throws IllegalStateException;

    K getKey(final V value) throws IllegalStateException;

    K save(final V object) throws IllegalStateException;

    void save(final UUID key, final V object) throws IllegalStateException;

    void delete(final K uuid) throws IllegalStateException;

    boolean exists(final K uuid);

    boolean contains(final V object);
}