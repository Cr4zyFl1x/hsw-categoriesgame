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

    List<Player> getPlayers();

    Player getAdmin();
    void setAdmin(Player player);

    void setCategories(List<String> categories);
    List<String> getCategories();

    int getCurrentRoundNumber();
    char getCurrentLetter();

    void startGame() throws InterruptedException;

    char startNewRound();

    boolean sendAnswers(List<String> answers, String playerName);

    void evaluateAnswers();

    int getPointsOfPlayer(Player player);

    Player getPlayerByName(String name);

    void changeAdmin();



}
