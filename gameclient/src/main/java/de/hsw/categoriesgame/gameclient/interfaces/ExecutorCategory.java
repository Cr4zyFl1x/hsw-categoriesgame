package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * @author Florian J. Kleine-Vorholt
 */
public enum ExecutorCategory {

    /**
     * Executed when player joins/leaves the lobby
     */
    PLAYER_JOIN_LEAVE,

    /**
     * Executed when the state of the current game changes
     */
    ROUND_STATE_CHANGE;

    @Override
    public String toString() {
        return this.name();
    }
}