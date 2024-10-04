package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.PlayingService;
import de.hsw.categoriesgame.gameserver.gamelogic.util.RandomLetterUtil;
import de.hsw.categoriesgame.gameserver.gamelogic.util.SortingUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayingServiceImpl implements PlayingService {

    private final Lobby lobby;
    private Game game;


    public PlayingServiceImpl(Lobby lobby) {
        this.lobby = lobby;
    }

    public PlayingServiceImpl(String lobbyCode) {
        this.lobby = new Lobby(lobbyCode);
    }


    @Override
    public void startGame() {
        game = new Game(lobby.getPlayers());

    }

    @Override
    public void endGame() {

    }

    @Override
    public char startNewRound() {
        game.updateRoundNumber();
        game.resetHasAnswered();
        return game.generateRandomLetter();
    }

    @Override
    public boolean sendAnswersOfRound(Player player, List<String> answersPerCategory) {
        // check if everyone answered
        var categoryAnswerMap = new HashMap<String, String>();
        for (int i = 0; i < answersPerCategory.size(); i++) {
            categoryAnswerMap.put(game.getCategories().get(i), answersPerCategory.get(i));
        }
        game.setAnswersOfPlayer(player, categoryAnswerMap);
        return game.haveAllPlayersAnswered();
    }

    @Override
    public void evaluateRound() {
        game.evaluateAnswers();
    }

    @Override
    public void setCategories(List<String> categories) {
        game.setCategories(categories);
    }

    @Override
    public List<String> getCategories() {
        return game.getCategories();
    }

    @Override
    public int getCurrentRoundNumber() {
        return game.getRoundNumber();
    }

    @Override
    public char getCurrentLetter() {
        return game.getCurrentLetter();
    }

    @Override
    public int getPointsOfPlayer(Player player) {
        return game.getCurrentPoints().get(player);
    }


}












