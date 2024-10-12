package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ClientImpl implements Client {
    private static final Logger log = LoggerFactory.getLogger(ClientImpl.class);
    private UUID uuid;
    private final String name;

    private int points;

    private boolean hasAnswered;

    public ClientImpl(String name) {
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
    public void notifyPlayerAboutRoundState(RoundState roundState) {
        System.out.println("Neuer State: " + roundState.name());
    }

    @Override
    public void notifyPlayerAboutLobbyState() {
        log.info("Lobby notification!");
    }

    //-----------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final ClientImpl other = (ClientImpl) obj;
        return this.uuid.equals(other.uuid);
    }
}
