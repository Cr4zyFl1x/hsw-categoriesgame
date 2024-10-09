package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.DoubtedAnswer;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.Round;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.RoundEntry;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.util.RandomLetterUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameImpl implements Game {

    @Getter
    @Setter
    private GameConfigs gameConfigs;
    @Getter
    private int roundNumber;

    @Getter
    private char currentLetter;

    private final List<Player> players;

    @Getter
    private final List<String> categories;

    private final Round round;

    @Setter
    private boolean answersWereDoubted;

    public GameImpl(List<Player> players, GameConfigs gameConfigs) {
        this.players = players;
        this.categories = new ArrayList<>();
        this.gameConfigs = gameConfigs;

        this.round = new Round(currentLetter);

        resetPoints();
        resetRoundNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateRoundNumber() {
        this.roundNumber += 1;
    }

    private void resetRoundNumber() {
        this.roundNumber = 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPointsForPlayer(Player player, PointRules pointRule) {
        var withAddedPoints = player.getPoints() + pointRule.getPoints();
        player.setPoints(withAddedPoints);
    }

    private void resetPoints() {
        players.forEach(player -> player.setPoints(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCategories(List<String> categories) {
        this.categories.addAll(categories);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAnswers(List<NormalAnswer> normalAnswers) {
        normalAnswers.forEach(normalAnswer -> {
            var category = normalAnswer.category();
            var player = getPlayerByUUID(normalAnswer.playerUUID());
            var answer = normalAnswer.answer();

            round.addEntry(category, player, answer);
            player.setHasAnswered(true);
        });

    }

    @Override
    public List<RoundEntry> doubtAnswer(DoubtedAnswer doubtedAnswer) {
        var playerOfAnswerUUID = doubtedAnswer.playerOfAnswerUUID();
        var player = getPlayerByUUID(playerOfAnswerUUID);
        var category = doubtedAnswer.category();
        var doubtedBy = getPlayerByUUID(doubtedAnswer.doubtingPlayerUUID());

        var entry = round.getEntry(player, category);
        entry.doubtAnswer(doubtedBy);
        return round.getRoundEntries();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoundEntry> evaluateAnswers() {
        if (!this.haveAllPlayersAnswered() || !this.answersWereDoubted) {
            return round.getRoundEntries();
        }

        categories.forEach(category -> {
            var entriesOfCategory = round.getEntriesOfCategory(category);

            entriesOfCategory.forEach(roundEntry -> {
                if (!validInput(roundEntry.getAnswer())) {
                    roundEntry.setAnswer("");
                }
            });

            var numberOfBlanks = entriesOfCategory.stream()
                    .filter(roundEntry -> roundEntry.getAnswer().isEmpty()).count();

            var amountOfAnswers = entriesOfCategory.stream()
                    .map(RoundEntry::getAnswer)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            entriesOfCategory.forEach(roundEntry -> {
                PointRules pointRules = getPointRules(players.size(), amountOfAnswers, numberOfBlanks, roundEntry.getAnswer(), roundEntry.getDoubtedBy().size());
                this.addPointsForPlayer(roundEntry.getPlayer(), pointRules);
            });

        });
        resetHasAnswered();
        return round.getRoundEntries();
    }

    private PointRules getPointRules(int amountOfPlayers, Map<String, Long> amountOfAnswers, long numberOfBlanks, String answer, int amountOfDoubted) {
        PointRules pointRules;

        if (answer.isBlank()) {
            pointRules = PointRules.NO_WORD;
        } else if (!validInput(answer)) {
            pointRules = PointRules.FALSE_WORD;
        } else if (amountOfDoubted >= gameConfigs.getMinDoubtingPlayers()) {
            pointRules = PointRules.FALSE_WORD;
        } else if (amountOfAnswers.get(answer) > 1) {
            pointRules = PointRules.MULTIPLE_WORD;
        } else if (amountOfAnswers.get(answer) == 1 && numberOfBlanks == amountOfPlayers - 1) {
            pointRules = PointRules.ONLY_PLAYER_WITH_WORD;
        } else {
            pointRules = PointRules.DISTINCT_WORD;
        }
        return pointRules;
    }

    private boolean validInput(String inputString) {
        return inputString.startsWith(String.valueOf(this.currentLetter));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char generateRandomLetter() {
        currentLetter = RandomLetterUtil.getRandomLetter();
        return currentLetter;
    }

    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean haveAllPlayersAnswered() {
        var answered = players.stream().filter(Player::hasAnswered).toList();
        return players.size() == answered.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetHasAnswered() {
        players.forEach(player -> player.setHasAnswered(false));
    }

    @Override
    public int getCurrentPointsOfPlayer(Player player) {
        return player.getPoints();
    }

    @Override
    public boolean answersWereDoubted() {
        return answersWereDoubted;
    }

    private Player getPlayerByUUID(UUID uuid) {
        var optionalPlayer = players.stream().filter(player -> player.getUUID().equals(uuid)).findFirst();
        return optionalPlayer.orElse(null);
    }

}
