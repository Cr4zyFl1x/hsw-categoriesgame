package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.rpc.Registry;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RegistryBase<T> implements Registry<UUID, T> {

    final Hashtable<UUID, T> registry = new Hashtable<>();

    @Override
    public T get(UUID uuid) throws IllegalStateException {
        if (!exists(uuid)) {
            throw new IllegalStateException("Registry does not contain entry for UUID: " + uuid);
        }
        return registry.get(uuid);
    }

    @Override
    public UUID getKey(T value) throws IllegalStateException {
        final IllegalStateException ex = new IllegalStateException("Registry does not contain entry for value: " + value);
        if (!contains(value)) {
            throw ex;
        }
        for (Map.Entry<UUID, T> entry : registry.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        throw ex;
    }

    @Override
    public UUID save(T object) throws IllegalStateException {
        if (contains(object)) {
            throw new IllegalStateException("Registry already contains this entry under UUID: " + getKey(object));
        }
        final UUID uuid = UUID.randomUUID();
        registry.put(uuid, object);
        return uuid;
    }

    @Override
    public void save(UUID key, T object) throws IllegalStateException {
        if (contains(object) || exists(key)) {
            throw new IllegalStateException("Registry already contains an entry with this key or for this value.");
        }
        registry.put(key, object);
    }


    @Override
    public void delete(UUID uuid) throws IllegalStateException
    {
        if (!exists(uuid)) {
            throw new IllegalStateException("Registry does not contain entry for UUID: " + uuid);
        }
        registry.remove(uuid);
    }

    @Override
    public boolean exists(UUID uuid) {
        return registry.containsKey(uuid);
    }

    @Override
    public boolean contains(T object) {
        return registry.containsValue(object);
    }
}