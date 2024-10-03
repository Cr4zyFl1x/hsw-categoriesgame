package de.hsw.categoriesgame.gameapi.rpc;

/**
 * Stores values for specific type of keys and isolates the methods for access to them
 *
 * @param <K> The type of the key
 * @param <V> The type of the value
 *
 * @author Florian J. Kleine-Vorholt
 */
public interface Registry<K, V> {

    /**
     * Gets the value for the desired key.
     *
     * @param key   the key to get the value for
     *
     * @return      The value for the key
     *
     * @throws IllegalStateException if no value was found for the key
     */
    V get(final K key) throws IllegalStateException;

    /**
     * Gets the key of a value
     *
     * @param value the value
     *
     * @return      the eky for the value
     *
     * @throws IllegalStateException if Registry does not contain the value
     */
    K getKey(final V value) throws IllegalStateException;

    /**
     * Saves the value in the registry and returns the generated key for it
     *
     * @param object    the value to save
     *
     * @return          the key of the newly saved value
     *
     * @throws IllegalStateException if the Registry already contains the value
     */
    K save(final V object) throws IllegalStateException;

    /**
     * Saves a value with a desired key
     *
     * @param key       the desired key
     * @param object    the value to save
     *
     * @throws IllegalStateException if the Registry already contains the key or value
     */
    void save(final K key, final V object) throws IllegalStateException;

    /**
     * Deletes a key-value-pair from the Registry
     *
     * @param key   the key of the pair to delete
     *
     * @throws IllegalStateException if the desired key does not exist
     */
    void delete(final K key) throws IllegalStateException;

    /**
     * Checks if a key exists in the Registry
     *
     * @param key   the key to check
     *
     * @return  true, if Registry contains the desired key
     */
    boolean exists(final K key);

    /**
     * Checks if a value exists in the Registry
     *
     * @param object    the value to check
     *
     * @return  true, if Registry contains the value
     */
    boolean contains(final V object);
}