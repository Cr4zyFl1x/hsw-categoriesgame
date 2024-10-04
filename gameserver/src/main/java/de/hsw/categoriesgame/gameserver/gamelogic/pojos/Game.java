package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.util.RandomLetterUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Game {

    @Getter
    private int roundNumber;

    @Getter
    private char currentLetter;

    @Getter
    private final Map<Player, Integer> currentPoints;
    private final List<Player> players;

    @Getter
    @Setter
    private List<String> categories;

    private final Map<String, Map<Player, String>> currentAnswers;

    public Game(List<Player> players) {
        this.currentPoints = new HashMap<>();
        this.currentAnswers = new HashMap<>();
        this.players = players;
        this.categories = new ArrayList<>();

        // setup
        players.forEach(player -> this.currentPoints.put(player, 0));
        resetPoints();
        resetRoundNumber();
        this.addCategoriesToCurrentAnswer();
        this.addPlayersToCurrentAnswer();
    }

    public void updateRoundNumber() {
        this.roundNumber += 1;
    }

    public void resetRoundNumber() {
        this.roundNumber = 0;
    }

    public void addPointsForPlayer(Player player, PointRules pointRule) {
        var withAddedPoints = this.currentPoints.get(player) + pointRule.getPoints();
        this.currentPoints.replace(player, withAddedPoints);
    }

    public void resetPoints() {
        this.currentPoints.clear();
        players.forEach(player -> this.currentPoints.replace(player, 0));
    }

    // Answers
    private void addCategoriesToCurrentAnswer() {
        categories.forEach(category -> currentAnswers.put(category, new HashMap<>()));
    }

    private void addPlayersToCurrentAnswer() {
        currentAnswers.forEach((category, playerAnswerMap) -> {
            players.forEach(player -> playerAnswerMap.put(player, ""));
        });
    }

    public void setAnswersOfPlayer(Player player, Map<String, String> answersPerCategory) {
        currentAnswers.forEach((category, playerAnswerMap) -> {
            var answer = answersPerCategory.get(category);
            playerAnswerMap.replace(player, answer);
        });
        player.setHasAnswered(true);
    }

    public void evaluateAnswers() {
        currentAnswers.forEach((category, playerAnswerMap) -> {
            playerAnswerMap.replaceAll((player, answer) -> validInput(answer) ? answer : "");

            var map = playerAnswerMap.values().stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            playerAnswerMap.forEach((player, answer) -> {
                PointRules pointRules;

                if (answer.isBlank()) {
                    pointRules = PointRules.NO_WORD;
                } else if (!validInput(answer)) {
                    pointRules = PointRules.FALSE_WORD;
                } else if (map.get(answer) > 1) {
                    pointRules = PointRules.MULTIPLE_WORD;
                } else if (map.get(answer) == 1 && map.get("") == playerAnswerMap.size() - 1) {
                    pointRules = PointRules.ONLY_PLAYER_WITH_WORD;
                } else {
                    pointRules = PointRules.DISTINCT_WORD;
                }

                this.addPointsForPlayer(player, pointRules);
            });
        });
    }

    private boolean validInput(String inputString) {
        return inputString.startsWith(String.valueOf(this.currentLetter));
    }

    public char generateRandomLetter() {
        currentLetter = RandomLetterUtil.getRandomLetter();
        return currentLetter;
    }

    public boolean haveAllPlayersAnswered() {
        var answered = players.stream().filter(Player::hasAnswered).toList();
        return players.size() == answered.size();
    }

    public void resetHasAnswered() {
        players.forEach(player -> player.setHasAnswered(false));
    }

}
