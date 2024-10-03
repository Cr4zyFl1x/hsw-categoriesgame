package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.rpc.Registry;

import java.util.Hashtable;
import java.util.Map;

/**
 * Abstract {@link RegistryBase}
 *
 * @author Florian J. Kleine-Vorholt
 */
public abstract class RegistryBase<K, T> implements Registry<K, T> {

    /**
     * The Hashtable that stores the key-value-pairs
     */
    final Hashtable<K, T> registry = new Hashtable<>();



    /**
     * {@inheritDoc}
     */
    @Override
    public T get(K key) throws IllegalStateException
    {
        if (!exists(key)) {
            throw new IllegalStateException("Registry does not contain entry for key: " + key);
        }
        return registry.get(key);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public K getKey(T value) throws IllegalStateException
    {
        final IllegalStateException ex = new IllegalStateException("Registry does not contain entry for value: " + value);
        if (!contains(value)) {
            throw ex;
        }
        for (Map.Entry<K, T> entry : registry.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw ex;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public K save(T object) throws IllegalStateException, UnsupportedOperationException
    {
        if (contains(object)) {
            throw new IllegalStateException("Registry already contains this entry under key: " + getKey(object));
        }
        final K key = generateKey();
        registry.put(key, object);
        return key;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void save(K key, T object) throws IllegalStateException
    {
        if (contains(object) || exists(key)) {
            throw new IllegalStateException("Registry already contains an entry with this key or for this value.");
        }
        registry.put(key, object);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(K key) throws IllegalStateException
    {
        if (!exists(key)) {
            throw new IllegalStateException("Registry does not contain entry for key: " + key);
        }
        registry.remove(key);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(K key)
    {
        return registry.containsKey(key);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean contains(T object)
    {
        return registry.containsValue(object);
    }


    /**
     * Generates a new Key that does not exist in the Registry
     *
     * @return the generated key
     *
     * @throws UnsupportedOperationException if key generation is not supported
     */
    public abstract K generateKey() throws UnsupportedOperationException;
}