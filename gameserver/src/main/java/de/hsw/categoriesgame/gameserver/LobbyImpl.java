package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class LobbyImpl implements Lobby {

    @Getter
    private final String lobbyCode;
    @Getter
    private final List<Player> players;

    @Setter
    @Getter
    private Player admin;

    private Game game;

    public LobbyImpl(String lobbyCode)
    {
        this.lobbyCode = lobbyCode;
        this.players = new ArrayList<>();
    }

    @Override
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
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
    public void startGame() {
        game = new GameImpl(getPlayers());
    }

    @Override
    public char startNewRound() {
        game.updateRoundNumber();
        game.resetHasAnswered();
        return game.generateRandomLetter();
    }

    @Override
    public boolean sendAnswers(List<String> answers, String playerName) {
        var player = getPlayerByName(playerName);
        // check if everyone answered
        var categoryAnswerMap = new HashMap<String, String>();
        for (int i = 0; i < answers.size(); i++) {
            categoryAnswerMap.put(game.getCategories().get(i), answers.get(i));
        }
        game.setAnswersOfPlayer(player, categoryAnswerMap);
        return game.haveAllPlayersAnswered();
    }

    @Override
    public void evaluateAnswers() {
        game.evaluateAnswers();
    }

    @Override
    public int getPointsOfPlayer(Player player) {
        return game.getCurrentPoints().get(player);
    }

    @Override
    public Player getPlayerByName(String name) {
        return players.stream().filter(pl -> pl.getName().equals(name)).toList().get(0);
    }

    @Override
    public void changeAdmin() {
        if (!players.isEmpty()) {
            this.admin = players.get(0);
        }
    }


    ///////////////////////////////////////////
    ///////////////////////////////////////////

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyImpl lobby = (LobbyImpl) o;
        return Objects.equals(lobbyCode, lobby.lobbyCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(lobbyCode);
    }

}