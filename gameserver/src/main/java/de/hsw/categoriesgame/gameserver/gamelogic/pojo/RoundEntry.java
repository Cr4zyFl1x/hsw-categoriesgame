package de.hsw.categoriesgame.gameserver.gamelogic.pojo;


import de.hsw.categoriesgame.gameapi.api.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RoundEntry {

    @Getter
    private final String category;

    @Getter
    private final Player player;

    @Getter
    @Setter
    private String answer;

    @Getter
    private boolean doubted;

    @Getter
    private final List<Player> doubtedBy;

    public RoundEntry(String category, Player player, String answer) {
        this.category = category;
        this.player = player;
        this.answer = answer;
        this.doubtedBy = new ArrayList<>();
    }

    /**
     * Doubt an answer.
     * @param doubtingPlayer    player that doubted the answer
     */
    public void doubtAnswer(Player doubtingPlayer) {
        this.doubted = true;
        doubtedBy.add(doubtingPlayer);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final RoundEntry other = (RoundEntry) obj;
        return this.category.equals(other.category) && this.player.equals(other.player);
    }
}
