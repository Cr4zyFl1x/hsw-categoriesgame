package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.pojo.RoundState;

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
     * Check if the player has already answered for this round.
     * @return  true if player has answered for the round
     */
    boolean hasAnswered();

    /**
     * Set answer status of player
     * @param hasAnswered   true if they have answered, false if not answered
     */
    void setHasAnswered(boolean hasAnswered);

    /**
     * Notifies the client of the new state
     *
     * @param roundState
     */
    void notifyRoundState(GameRoundState roundState);

    void notifyPlayerAboutRoundState(RoundState roundState);


    void notifyPlayerAboutLobbyState();
}