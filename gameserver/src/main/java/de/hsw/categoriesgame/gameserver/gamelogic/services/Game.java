package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.DoubtedAnswer;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.RoundEntry;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;

import java.util.List;
import java.util.Map;

public interface Game {

    void startNewRound();
    void closeAnsweringRound();
    void startDoubtingRound();
    void closeDoubtingRound();

    GameConfigs getGameConfigs();
    void setGameConfigs(GameConfigs gameConfigs);

    /**
     * Set the categories for the game.
     * @param categories    list of categories
     */
    void setCategories(List<String> categories);

    /**
     * Send answers as normal answers
     * @param normalAnswers list of normal answers
     */
    void sendAnswers(List<NormalAnswer> normalAnswers);

    /**
     * Doubts an answer.
     * @param doubtedAnswer object of DoubtedAnswer
     * @return              list of RoundEntry
     */
    List<RoundEntry> doubtAnswer(DoubtedAnswer doubtedAnswer);

    // Getter
    List<String> getCategories();
    int getRoundNumber();
    char getCurrentLetter();
    int getCurrentPointsOfPlayer(Player player);

    boolean answersWereDoubted();
    void setAnswersWereDoubted(boolean answersWereDoubted);
}
