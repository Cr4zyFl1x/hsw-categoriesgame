package de.hsw.categoriesgame.gameserver.gamelogic.services;


import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface PlayingService {

    void startGame();

    void endGame();

    char startNewRound();

    boolean sendAnswersOfRound(Player player, List<String> answersPerCategory);

    void evaluateRound();

    void setCategories(List<String> categories);
    List<String> getCategories();
    int getCurrentRoundNumber();
    char getCurrentLetter();

    int getPointsOfPlayer(Player player);

}
