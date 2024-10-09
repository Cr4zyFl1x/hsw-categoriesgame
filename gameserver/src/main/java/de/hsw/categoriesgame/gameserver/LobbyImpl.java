package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.*;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import lombok.Getter;

import java.util.*;

public class LobbyImpl implements Lobby {

    @Getter
    private final String lobbyCode;
    @Getter
    private final List<Player> players;

    @Getter
    private Player admin;

    private Game game;

    public LobbyImpl(String lobbyCode)
    {
        this.lobbyCode = lobbyCode;
        this.players = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removePlayer(Player player) {
        this.players.remove(player);
        if (player.equals(admin)) {
            changeAdmin();
        }
    }

    @Override
    public void setGameConfigs(GameConfigs gameConfigs) {
        game.setGameConfigs(gameConfigs);
    }

    @Override
    public GameConfigs getGameConfigs() {
        return game.getGameConfigs();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCategories(List<String> categories) {
        game.setCategories(categories);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        game = new GameImpl(getPlayers(), getGameConfigs());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public char startNewRound() {
        game.updateRoundNumber();
        game.resetHasAnswered();
        return game.generateRandomLetter();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendAnswers(List<NormalAnswer> normalAnswers) {
        game.sendAnswers(normalAnswers);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> doubtAnswer(DoubtedAnswer doubtedAnswer) {
        var list = new ArrayList<Entry>();
        game.doubtAnswer(doubtedAnswer).forEach(roundEntry -> {
            var category = roundEntry.getCategory();
            var playerUUID = roundEntry.getPlayer().getUUID();
            var answer = roundEntry.getAnswer();
            var doubted = roundEntry.isDoubted();
            var doubtedBy = roundEntry.getDoubtedBy().stream().map(Player::getUUID).toList();
            list.add(new Entry(category, playerUUID, answer, doubted, doubtedBy));
        });
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entry> evaluateAnswers() {
        var list = new ArrayList<Entry>();
        game.evaluateAnswers().forEach(roundEntry -> {
            var category = roundEntry.getCategory();
            var playerUUID = roundEntry.getPlayer().getUUID();
            var answer = roundEntry.getAnswer();
            var doubted = roundEntry.isDoubted();
            var doubtedBy = roundEntry.getDoubtedBy().stream().map(Player::getUUID).toList();
            list.add(new Entry(category, playerUUID, answer, doubted, doubtedBy));
        });
        return list;
    }

    @Override
    public int getPointsOfPlayer(Player player) {
        return game.getCurrentPointsOfPlayer(player);
    }

    /**
     * {@inheritDoc}
     */
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