package de.hsw.categoriesgame.gameserver.domain;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.GameData;
import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
    public void notifyRoundState(GameRoundState roundState, GameData gameData)
    {

    }

    @Override
    public void notifyPlayerJoinLeave(List<PlayerBean> players) {

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
