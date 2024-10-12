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
     * Players can now see the answers from the others and can decide if they are correct or not
     */
    DOUBTING_OPEN,

    /**
     * Players have to show the final results
     */
    FINAL_RESULTS
}