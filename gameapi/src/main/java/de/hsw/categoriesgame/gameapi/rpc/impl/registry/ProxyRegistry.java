package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.rpc.ProxyData;

import java.util.UUID;

/**
 * {@code ProxyRegistry} used to save Proxies and make them reusable
 *
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyRegistry extends RegistryBase<UUID, ProxyData> {

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID generateKey() {
        return UUID.randomUUID();
    }
}