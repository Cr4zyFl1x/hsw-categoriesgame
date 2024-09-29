package de.hsw.categoriesgame.gameapi.api;

import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface Player {

    UUID getUUID();

    String getName();
}
