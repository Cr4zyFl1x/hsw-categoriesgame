package de.hsw.categoriesgame.gameapi.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * Record as POJO to transfer single entry from server to client.
 * @param category      category of answer
 * @param playerUUID    uuid of player that answered
 * @param answer        original answer
 * @param doubted       if answer was doubted
 * @param doubtedBy     list of uuids of players that doubted the answer
 */
public record Entry(String category, UUID playerUUID, String answer, boolean doubted, List<UUID> doubtedBy) implements Serializable {
}
