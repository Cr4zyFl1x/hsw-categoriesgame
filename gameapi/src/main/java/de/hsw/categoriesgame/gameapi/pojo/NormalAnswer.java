package de.hsw.categoriesgame.gameapi.pojo;

import de.hsw.categoriesgame.gameapi.api.Player;

import java.io.Serializable;
import java.util.UUID;

public record NormalAnswer(UUID playerUUID, String category, String answer) implements Serializable {
}
