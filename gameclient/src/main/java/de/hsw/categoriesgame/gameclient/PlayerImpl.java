package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.Player;

import java.util.UUID;

public class PlayerImpl implements Player {

    private final UUID uuid;
    private final String name;

    private boolean hasAnswered;

    public PlayerImpl(final String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID() {
        return uuid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        System.out.println(name);
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAnswered() {
        return hasAnswered;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setHasAnswered(boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }
}