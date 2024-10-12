package de.hsw.categoriesgame.gameapi.pojo;

public enum RoundState {
    ANSWERING_OPEN,

    /**
     * Alle sollen Antworten senden
     */
    ANSWERING_CLOSED,
    DOUBTING_OPEN,
    DOUBTING_UPDATE_NEEDED,
    DOUBTING_CLOSED,
    CLOSED;
}
