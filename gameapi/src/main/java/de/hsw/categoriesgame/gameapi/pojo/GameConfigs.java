package de.hsw.categoriesgame.gameapi.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GameConfigs {

    @Getter
    private final int maxRounds;

    @Getter
    private final int maxPlayers;

    @Getter
    private final int minDoubtingPlayers;

    @Getter
    @Setter
    private List<String> categories;

    public GameConfigs(int maxRounds, int maxPlayers, int minDoubtingPlayers) {
        this.maxRounds = maxRounds;
        this.maxPlayers = maxPlayers;
        this.minDoubtingPlayers = minDoubtingPlayers;
        this.categories = new ArrayList<>();
    }

    public GameConfigs(int maxRounds, int maxPlayers) {
        this.maxRounds = maxRounds;
        this.maxPlayers = maxPlayers;
        this.minDoubtingPlayers = 1;
        this.categories = new ArrayList<>();
    }
}
