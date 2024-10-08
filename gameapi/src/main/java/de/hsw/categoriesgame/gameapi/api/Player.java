package de.hsw.categoriesgame.gameapi.api;

import java.util.UUID;

public interface Player {

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

    int getPoints();
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
}
