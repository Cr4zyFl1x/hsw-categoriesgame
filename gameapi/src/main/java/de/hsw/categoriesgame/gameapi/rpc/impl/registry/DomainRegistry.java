package de.hsw.categoriesgame.gameapi.rpc.impl.registry;

import java.util.UUID;

/**
 * DomainRegistry to store the Objects that are handled by a RemoteServer
 *
 * @author Florian J. Kleine-Vorholt
 */
public final class DomainRegistry extends RegistryBase<UUID, Object> {

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID generateKey() throws UnsupportedOperationException
    {
        return UUID.randomUUID();
    }
}