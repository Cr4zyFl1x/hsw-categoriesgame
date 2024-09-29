package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class DomainRegistry extends RegistryBase<UUID, Object> {

    @Override
    public UUID generateKey() {
        return UUID.randomUUID();
    }

}