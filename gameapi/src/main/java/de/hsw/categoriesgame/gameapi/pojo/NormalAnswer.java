package de.hsw.categoriesgame.gameapi.pojo;

import java.io.Serializable;
import java.util.UUID;

/**
 * Record as POJO to tranfer a normal answer to the server.
 * @param playerUUID    uuid of the player that answered
 * @param category      category of the answer
 * @param answer        original answer
 */
public record NormalAnswer(UUID playerUUID, String category, String answer) implements Serializable {
}
