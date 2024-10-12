package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.*;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.Round;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.RoundEntry;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.util.RandomLetterUtil;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameImpl implements Game {

    private static final Logger log = LoggerFactory.getLogger(GameImpl.class);
    @Getter
    @Setter
    private GameConfigs gameConfigs;
    @Getter
    private int roundNumber;

    private final List<Client> clients;
    private final List<Client> answered = new ArrayList<>();

    @Getter
    private final List<String> categories;

    @Getter
    private Round round;

    private RoundState roundState;

    @Setter
    private boolean answersWereDoubted;

    public GameImpl(List<Client> clients, GameConfigs gameConfigs) {
        this.clients = clients;
        this.categories = new ArrayList<>();
        this.gameConfigs = gameConfigs;

        //this.round = new Round(currentLetter);

        resetPoints();
        resetRoundNumber();
    }

    @Override
    public void startNewRound() {
        clearAnswers();
        this.updateRoundNumber();
        this.round = new Round(generateRandomLetter());

        this.roundState = RoundState.ANSWERING_OPEN;
        this.notifyPlayersOfState();
    }

    @Override
    public void closeAnsweringRound() {
        this.roundState = RoundState.ANSWERING_OPEN;
        this.notifyPlayersOfState();
    }

    @Override
    public void startDoubtingRound() {

    }

    @Override
    public void closeDoubtingRound() {
        this.roundState = RoundState.DOUBTING_CLOSED;
        this.notifyPlayersOfState();
        evaluateAnswers();
    }


    private void updateRoundNumber() {
        this.roundNumber += 1;
    }

    private void resetRoundNumber() {
        this.roundNumber = 1;
    }


    private void addPointsForPlayer(Client client, PointRules pointRule) {
        var withAddedPoints = client.getPoints() + pointRule.getPoints();
        client.setPoints(withAddedPoints);
    }

    private void resetPoints() {
        clients.forEach(player -> player.setPoints(0));
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
        if (this.roundState == RoundState.ANSWERING_OPEN || this.roundState == RoundState.ANSWERING_CLOSED) {

            Client client = getPlayerByUUID(normalAnswers.get(0).playerUUID());

            normalAnswers.forEach(normalAnswer -> {
                var category = normalAnswer.category();
                var answer = normalAnswer.answer();
                round.addEntry(category, client, answer);
            });


            if (noneAnswered()) {
                setAnswered(client);

                this.roundState = RoundState.ANSWERING_CLOSED;

                this.notifyPlayersOfState();
            }
        }

        log.debug("{} Players answered", answered.size());

        if (allAnswered()) {
            log.debug("All players answered.");
            this.roundState = RoundState.DOUBTING_OPEN;
            this.notifyPlayersOfState();
        }
    }



    @Override
    public List<RoundEntry> doubtAnswer(DoubtedAnswer doubtedAnswer) {
        if (this.roundState != RoundState.DOUBTING_OPEN) {
            return round.getRoundEntries();
        }
        var playerOfAnswerUUID = doubtedAnswer.playerOfAnswerUUID();
        var player = getPlayerByUUID(playerOfAnswerUUID);
        var category = doubtedAnswer.category();
        var doubtedBy = getPlayerByUUID(doubtedAnswer.doubtingPlayerUUID());

        var entry = round.getEntry(player, category);
        entry.doubtAnswer(doubtedBy);
        this.notifyPlayersOfState(RoundState.DOUBTING_UPDATE_NEEDED);
        return round.getRoundEntries();
    }



    @Override
    public char getCurrentLetter()
    {
        return round.getLetter();
    }


    /**
     * TEST
     */
    public void setCurrentLetter() {
        round.setLetter('A');
    }

    private void evaluateAnswers() {
        if (this.roundState != RoundState.DOUBTING_CLOSED) {
            return;
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
                PointRules pointRules = getPointRules(clients.size(), amountOfAnswers, numberOfBlanks, roundEntry.getAnswer(), roundEntry.getDoubtedBy().size());
                this.addPointsForPlayer(roundEntry.getClient(), pointRules);
            });
        });
    }

    private PointRules getPointRules(int amountOfPlayers, Map<String, Long> amountOfAnswers, long numberOfBlanks, String answer, int amountOfDoubted) {
        PointRules pointRules;

        if (answer.isBlank()) {
            pointRules = PointRules.NO_WORD;
        } else if (!validInput(answer)) {
            pointRules = PointRules.FALSE_WORD;
        } else if (amountOfDoubted >= amountOfPlayers / 2) {
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
        return inputString.startsWith(String.valueOf(this.round.getLetter()));
    }

    private char generateRandomLetter()
    {
        return RandomLetterUtil.getRandomLetter();
    }


    @Override
    public int getCurrentPointsOfPlayer(Client client) {
        return client.getPoints();
    }

    @Override
    public boolean answersWereDoubted() {
        return answersWereDoubted;
    }

    private Client getPlayerByUUID(UUID uuid) {
        var optionalPlayer = clients.stream().filter(player -> player.getUUID().equals(uuid)).findFirst();
        return optionalPlayer.orElse(null);
    }

    private void notifyPlayersOfState() {
        clients.forEach(player -> player.notifyPlayerAboutRoundState(this.roundState));
    }
    private void notifyPlayersOfState(RoundState roundState) {
        clients.forEach(player -> player.notifyPlayerAboutRoundState(roundState));
    }





    private void setAnswered(Client client) {
        if (answered.contains(client)) {
            return;
        }
        answered.add(client);
    }
    private boolean hasAnswered(Client client) {
        return answered.contains(client);
    }
    private void clearAnswers() {
        answered.clear();
    }
    private boolean allAnswered()
    {
        return answered.size() == clients.size();
    }
    private boolean noneAnswered()
    {
        return answered.isEmpty();
    }
}
