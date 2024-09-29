package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import de.hsw.categoriesgame.gameapi.rpc.ProxyData;

import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyRegistry extends RegistryBase<UUID, ProxyData> {

    @Override
    public UUID generateKey() {
        return UUID.randomUUID();
    }
}