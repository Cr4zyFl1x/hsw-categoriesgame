package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;

import java.util.List;
import java.util.Map;

public interface Game {

    void updateRoundNumber();
    void addPointsForPlayer(Player player, PointRules pointRule);
    void setCategories(List<String> categories);
    void setAnswersOfPlayer(Player player, Map<String, String> answersPerCategory);
    void evaluateAnswers();
    char generateRandomLetter();
    boolean haveAllPlayersAnswered();
    void resetHasAnswered();

    // Getter
    List<String> getCategories();
    int getRoundNumber();
    char getCurrentLetter();
    Map<Player, Integer> getCurrentPoints();
}
