package de.hsw.categoriesgame.gameapi.api;

import java.util.List;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 *
 * TODO: THIS IS ONLY A CLASS FOR TESTING PURPOSES! SHOULD BE REPLACED BY ACTUAL ONE
 *
 */
public interface Player {

    UUID getUUID();

    String getName();

    boolean hasAnswered();

    void setHasAnswered(boolean hasAnswered);
}
