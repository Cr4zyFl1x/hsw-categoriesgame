package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;

import java.util.UUID;

public class PlayerImpl implements Player {

    private final UUID uuid;
    private final String name;

    private int points;

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
    public int getPoints() {
        return points;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPoints(int points) {
        this.points = points;
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

    @Override
    public void notifyPlayer(RoundState roundState) {
        System.out.println("Neuer State: " + roundState.name());
    }
}