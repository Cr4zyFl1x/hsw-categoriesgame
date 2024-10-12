package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;

import java.util.List;
import java.util.UUID;

public class PlayerImpl implements Player {
    private UUID uuid;
    private final String name;

    private int points;

    private boolean hasAnswered;

    public PlayerImpl(String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public void setPoints(int points) {
        this.points = points;
    }


    @Override
    public boolean hasAnswered() {
        return hasAnswered;
    }

    @Override
    public void setHasAnswered(boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }

    @Override
    public void notifyPlayer(RoundState roundState) {
        System.out.println("Neuer State: " + roundState.name());
    }

    //-----------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final PlayerImpl other = (PlayerImpl) obj;
        return this.uuid.equals(other.uuid);
    }
}
