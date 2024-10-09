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

    /**
     * Uptades the round number by one (starting by 1).
     */
    void updateRoundNumber();

    /**
     * Adds points to the player for their answers.
     * @param player        player that will get the points
     * @param pointRule     given situation that translate to points
     */
    void addPointsForPlayer(Player player, PointRules pointRule);

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

    /**
     * Evaluates answers of all players if all have answered and gives points.
     * @return  list of RoundEntry
     */
    List<RoundEntry> evaluateAnswers();

    /**
     * Generates a random letter from A-Z for each new round.
     * @return  random letter from A-Z
     */
    char generateRandomLetter();

    /**
     * Checks if all players have sent their answers.
     * @return  if all players have answered
     */
    boolean haveAllPlayersAnswered();

    /**
     * Resets boolean if all players have answered to false for all.
     */
    void resetHasAnswered();

    // Getter
    List<String> getCategories();
    int getRoundNumber();
    char getCurrentLetter();
    int getCurrentPointsOfPlayer(Player player);

    boolean answersWereDoubted();
    void setAnswersWereDoubted(boolean answersWereDoubted);
}
