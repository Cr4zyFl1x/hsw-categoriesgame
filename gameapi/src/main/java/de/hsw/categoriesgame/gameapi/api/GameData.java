package de.hsw.categoriesgame.gameapi.api;

import lombok.Getter;

import java.io.Serializable;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameData implements Serializable {

    @Getter
    private final char  currentLetter;

    @Getter
    private final int   currentRound;

    public GameData(final char currentLetter, final int currentRound)
    {
        this.currentLetter = currentLetter;
        this.currentRound = currentRound;
    }


}