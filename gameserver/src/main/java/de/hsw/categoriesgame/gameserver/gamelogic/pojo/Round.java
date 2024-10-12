package de.hsw.categoriesgame.gameserver.gamelogic.pojo;

import de.hsw.categoriesgame.gameapi.api.Player;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Round {

    @Getter
    @Setter
    private char letter;

    @Getter
    private List<RoundEntry> roundEntries;



    public Round(char letter) {
        this.letter = letter;
        this.roundEntries = new ArrayList<>();
    }

    /**
     * Adds an entry to the list of entries.
     * @param category  category fo answer
     * @param player    player that answered
     * @param answer    original answer
     * @return          if entry already existed in list
     */
    public boolean addEntry(String category, Player player, String answer) {
        RoundEntry entry = new RoundEntry(category, player, answer);
        if (!roundEntries.contains(entry)) {
            roundEntries.add(entry);
            return true;
        }
        return false;
    }

    /**
     * Get entries of specified category.
     * @param category  category of answers
     * @return          list of answers
     */
    public List<RoundEntry> getEntriesOfCategory(String category) {
        return roundEntries.stream()
                .filter(roundEntry -> roundEntry.getCategory().equals(category))
                .toList();
    }

    /**
     * Get an entry by player and category.
     * @param player    player that answered
     * @param category  category of answer
     * @return          object of class RoundEntry
     */
    public RoundEntry getEntry(Player player, String category) {
        var optionalEntry = roundEntries.stream()
                .filter(roundEntry -> roundEntry.getCategory().equals(category))
                .filter(roundEntry -> roundEntry.getPlayer().equals(player))
                .findFirst();

        return optionalEntry.orElseThrow();
    }

}
