package de.hsw.categoriesgame.gameapi.pojo;

import de.hsw.categoriesgame.gameapi.api.Player;

import java.io.Serializable;
import java.util.UUID;

public record DoubtedAnswer(UUID playerOfAnswerUUID, String category, String answer, UUID doubtingPlayerUUID) implements Serializable {
}
