package de.hsw.categoriesgame.gameapi.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public record Entry(String category, UUID playerUUID, String answer, boolean doubted, List<UUID> doubtedBy) implements Serializable {
}
