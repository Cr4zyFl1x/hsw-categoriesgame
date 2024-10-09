package de.hsw.categoriesgame.gameapi.pojo;

import java.io.Serializable;
import java.util.UUID;

/**
 * Record as POJO to send doubted answer to server.
 * @param playerOfAnswerUUID    uuid of player that answered
 * @param category              category of the answer
 * @param answer                original answer
 * @param doubtingPlayerUUID    uuid of doubting player
 */
public record DoubtedAnswer(UUID playerOfAnswerUUID, String category, String answer, UUID doubtingPlayerUUID) implements Serializable {
}
