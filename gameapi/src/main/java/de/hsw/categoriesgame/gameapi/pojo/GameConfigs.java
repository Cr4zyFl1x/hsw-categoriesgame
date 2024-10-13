package de.hsw.categoriesgame.gameapi.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class GameConfigs implements Serializable {

    private final int maxRounds;

    private final int maxPlayers;

    @Setter
    private List<String> categories;

    public GameConfigs(int maxRounds, int maxPlayers) {
        this.maxRounds = maxRounds;
        this.maxPlayers = maxPlayers;
        this.categories = new ArrayList<>();
    }
}
