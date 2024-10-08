package de.hsw.categoriesgame.gameserver.gamelogic.pojo;

import de.hsw.categoriesgame.gameapi.api.Player;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Round {

    @Getter
    private char letter;

    @Getter
    private List<RoundEntry> roundEntries;



    public Round(char letter) {
        this.letter = letter;
        this.roundEntries = new ArrayList<>();
    }

    public boolean addEntry(String category, Player player, String answer) {
        RoundEntry entry = new RoundEntry(category, player, answer);
        if (!roundEntries.contains(entry)) {
            roundEntries.add(entry);
            return true;
        }
        return false;
    }

    public List<RoundEntry> getEntriesOfCategory(String category) {
        return roundEntries.stream()
                .filter(roundEntry -> roundEntry.getCategory().equals(category))
                .toList();
    }

    List<RoundEntry> getEntriesOfPlayer(Player player) {
        return roundEntries.stream()
                .filter(roundEntry -> roundEntry.getPlayer().equals(player))
                .toList();
    }

    public RoundEntry getEntry(Player player, String category) {
        var optionalEntry = roundEntries.stream()
                .filter(roundEntry -> roundEntry.getCategory().equals(category))
                .filter(roundEntry -> roundEntry.getPlayer().equals(player))
                .findFirst();

        return optionalEntry.orElse(null);
        // TODO: 08.10.2024 Error

    }

}
