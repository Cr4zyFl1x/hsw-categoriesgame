package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;

import java.util.List;

public interface GameService {

    // Categories
    boolean addCategory(String category);
    boolean addCategories(List<String> categories);

    boolean removeCategory(String category);
    boolean removeCategories(List<String> categories);

    List<String> getAllCategories();

    // Points
    void evaluateAnswers(char currentLetter, List<List<String>> answersPerCategory);
    List<List<Integer>> getCurrentPoints();
    List<List<Integer>> getCurrentTopThreePlayersWithPoints();

    // Rounds
    void setAmountRounds(int amount);
    int getAmountRounds();

    void updateRoundNumber();

    char getCurrentLetter();
    char generateRandomLetter();

    Lobby getLobby();
}
