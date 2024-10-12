package de.hsw.categoriesgame.gameapi.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameConfigs implements Serializable {

    @Getter
    private final int maxRounds;

    @Getter
    private final int maxPlayers;

    @Getter
    @Setter
    private List<String> categories;

    public GameConfigs(int maxRounds, int maxPlayers) {
        this.maxRounds = maxRounds;
        this.maxPlayers = maxPlayers;
        this.categories = new ArrayList<>();
    }
}
