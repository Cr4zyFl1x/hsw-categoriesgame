package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.util.RandomLetterUtil;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameImpl implements Game {

    @Getter
    private int roundNumber;

    @Getter
    private char currentLetter;

    @Getter
    private final Map<Player, Integer> currentPoints;
    private final List<Player> players;

    @Getter
    private final List<String> categories;

    private final Map<String, Map<Player, String>> currentAnswers;

    public GameImpl(List<Player> players) {
        this.currentPoints = new HashMap<>();
        this.currentAnswers = new HashMap<>();
        this.players = players;
        this.categories = new ArrayList<>();

        // setup
        this.players.forEach(player -> this.currentPoints.put(player, 0));
        resetPoints();
        resetRoundNumber();
        this.addCategoriesToCurrentAnswer();
        this.addPlayersToCurrentAnswer();
    }

    public void updateRoundNumber() {
        this.roundNumber += 1;
    }

    private void resetRoundNumber() {
        this.roundNumber = 1;
    }

    @Override
    public void addPointsForPlayer(Player player, PointRules pointRule) {
        var withAddedPoints = this.currentPoints.get(player) + pointRule.getPoints();
        this.currentPoints.replace(player, withAddedPoints);
    }

    private void resetPoints() {
        this.currentPoints.clear();
        players.forEach(player -> this.currentPoints.replace(player, 0));
    }

    @Override
    public void setCategories(List<String> categories) {
        this.categories.addAll(categories);
        this.setupGame();
    }

    private void setupGame() {
        this.addCategoriesToCurrentAnswer();
        this.addPlayersToCurrentAnswer();
        this.players.forEach(player -> this.currentPoints.put(player, 0));
    }

    // Answers
    private void addCategoriesToCurrentAnswer() {
        categories.forEach(category -> currentAnswers.put(category, new HashMap<>()));
    }

    private void addPlayersToCurrentAnswer() {
        currentAnswers.forEach((category, playerAnswerMap) -> players.forEach(player -> playerAnswerMap.put(player, "")));
    }

    @Override
    public void setAnswersOfPlayer(Player player, Map<String, String> answersPerCategory) {
        currentAnswers.forEach((category, playerAnswerMap) -> {
            var answer = answersPerCategory.get(category);
            playerAnswerMap.replace(player, answer);
        });
        player.setHasAnswered(true);
    }

    @Override
    public void evaluateAnswers() {
        if (currentAnswers.isEmpty() || !this.haveAllPlayersAnswered()) {
            return;
        }

        currentAnswers.forEach((category, playerAnswerMap) -> {
            AtomicBoolean hasBlanks = new AtomicBoolean(false);

            playerAnswerMap.forEach((player, answer) -> {
                if (!validInput(answer)) {
                    playerAnswerMap.replace(player, "");
                    hasBlanks.set(true);
                }
            });

            var map = playerAnswerMap.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            int numberOfBlanks = 0;
            if (hasBlanks.get()) {
                numberOfBlanks = Math.toIntExact(map.get(""));
            }

            int finalNumberOfBlanks = numberOfBlanks;
            playerAnswerMap.forEach((player, answer) -> {
                PointRules pointRules = getPointRules(playerAnswerMap, hasBlanks, map, finalNumberOfBlanks, answer);
                this.addPointsForPlayer(player, pointRules);
            });
        });
    }

    private PointRules getPointRules(Map<Player, String> playerAnswerMap, AtomicBoolean hasBlanks, Map<String, Long> map, int finalNumberOfBlanks, String answer) {
        PointRules pointRules;

        if (answer.isBlank()) {
            pointRules = PointRules.NO_WORD;
        } else if (!validInput(answer)) {
            pointRules = PointRules.FALSE_WORD;
        } else if (map.get(answer) > 1) {
            pointRules = PointRules.MULTIPLE_WORD;
        } else if (map.get(answer) == 1 && hasBlanks.get() && finalNumberOfBlanks == playerAnswerMap.size() - 1) {
            pointRules = PointRules.ONLY_PLAYER_WITH_WORD;
        } else {
            pointRules = PointRules.DISTINCT_WORD;
        }
        return pointRules;
    }

    private boolean validInput(String inputString) {
        return inputString.startsWith(String.valueOf(this.currentLetter));
    }

    @Override
    public char generateRandomLetter() {
        currentLetter = RandomLetterUtil.getRandomLetter();
        return currentLetter;
    }

    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }

    @Override
    public boolean haveAllPlayersAnswered() {
        var answered = players.stream().filter(Player::hasAnswered).toList();
        return players.size() == answered.size();
    }

    @Override
    public void resetHasAnswered() {
        players.forEach(player -> player.setHasAnswered(false));
    }

}
