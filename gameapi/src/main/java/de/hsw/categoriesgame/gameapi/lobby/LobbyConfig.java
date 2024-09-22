package de.hsw.categoriesgame.gameapi.lobby;

/**
 * @author Florian J. Kleine-Vorholt
 */
public enum LobbyConfig {

    ROUNDS (Integer.class),
    CATEGORIES (String[].class),
    MAX_PLAYER_COUNT (Integer.class),
    ;


    LobbyConfig(Class<?> integerClass) {
    }
}
