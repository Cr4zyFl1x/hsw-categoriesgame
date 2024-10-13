package de.hsw.categoriesgame.gameclient.interfaces;

/**
 * @author Florian J. Kleine-Vorholt
 */
public enum ExecutorCategory {

    PLAYER_JOIN_LEAVE,
    ROUND_STATE_CHANGE;

    @Override
    public String toString() {
        return this.name();
    }
}
