package de.hsw.categoriesgame.gameapi.api;

/**
 * @author Florian J. Kleine-Vorholt
 */
public enum GameRoundState {

    /**
     * State before game has started
     */
    PREPARING,

    /**
     * Users can write their answers and have time to send them
     */
    ANSWERS_OPEN,

    /**
     * A player has send his answers as first.
     * All others have to send theirs now
     */
    ANSWERS_CLOSED,

    /**
     * Al players should switch to the answer overviews for the round
     */
    SHOW_ROUND_ANSWERS,


    /**
     * Players have to show the final results
     */
    FINAL_RESULTS,

    /**
     * When the penultimate player left is round over
     */
    PENULTIMATE_PLAYER_LEFT
}