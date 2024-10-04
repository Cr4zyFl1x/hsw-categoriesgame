package de.hsw.categoriesgame.gameapi.api;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 *
 * TODO: THIS IS ONLY A CLASS FOR TESTING PURPOSES! SHOULD BE REPLACED BY ACTUAL ONE
 *
 */
public interface Lobby {

    String getLobbyCode();

    void addPlayer(Player player);
    void removePlayer(Player player);

    List<Player> getAllPlayers();

    Player getAdmin();

    void setCategories(List<String> categories);
    List<String> getCategories();

    int getCurrentRoundNumber();
    char getCurrentLetter();

    void startGame();

    void startNewRound();

    void sendAnswers(List<String> answers, Player player);

    int evaluateAnswers();

    int getPointsOfPlayer(Player player);

}
