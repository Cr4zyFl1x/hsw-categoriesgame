package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;

import java.util.List;
import java.util.UUID;

public interface Client {

    /**
     * Get the UUID that was set when initializing the player.
     * @return  object of class UUID
     */
    UUID getUUID();

    /**
     * Get the name that was set when initializing the player.
     * @return  name of the player
     */
    String getName();

    /**
     * Get points of a player.
     * @return  points of player
     */
    int getPoints();

    /**
     * Sets points of player.
     * @param points    points of player
     */
    void setPoints(int points);

    /**
     * Notifies the client of the new state
     *
     * @param roundState
     */
    void notifyRoundState(GameRoundState roundState, GameData gameData);


    /**
     * Notifies the client about lobby joins/leaves
     * @param players
     */
    void notifyPlayerJoinLeave(List<PlayerBean> players);
}