package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.mapper.PlayerMapper;
import de.hsw.categoriesgame.gameserver.gamelogic.services.PlayingService;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.PlayingServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class LobbyImpl implements Lobby {

    private final String lobbyCode;
    private final List<Player> players;

    private Player admin;

    private PlayingService playingService;
    private Player player;

    public LobbyImpl(String lobbyCode, Player player)
    {
        this.lobbyCode = lobbyCode;
        this.players = new ArrayList<>();
        this.playingService = new PlayingServiceImpl(lobbyCode);
        this.admin = player;
        this.player = player;
    }


    @Override
    public String getLobbyCode()
    {
        return lobbyCode;
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
    public List<Player> getAllPlayers() {
        return this.players;
    }

    @Override
    public Player getAdmin() {
        return this.admin;
    }

    @Override
    public void setCategories(List<String> categories) {
        playingService.setCategories(categories);
    }

    @Override
    public List<String> getCategories() {
        return playingService.getCategories();
    }

    @Override
    public int getCurrentRoundNumber() {
        return playingService.getCurrentRoundNumber();
    }

    @Override
    public char getCurrentLetter() {
        return playingService.getCurrentLetter();
    }

    @Override
    public void startGame() {
        playingService.startGame();
    }

    @Override
    public void startNewRound() {
        playingService.startNewRound();
    }

    @Override
    public void sendAnswers(List<String> answers, Player player) {
        playingService.sendAnswersOfRound(PlayerMapper.map((PlayerImpl) player), answers);
    }

    @Override
    public int evaluateAnswers() {
        playingService.evaluateRound();
        return 0;
    }

    @Override
    public int getPointsOfPlayer(Player player) {
        return playingService.getPointsOfPlayer(PlayerMapper.map((PlayerImpl) player));
    }

    public void setPlayerId(Player p) {
        var player = (PlayerImpl) players.stream().filter(pl -> pl.getName().equals(p.getName())).toList().get(0);
        var position = players.indexOf(player);
        player.setId(position + 1);
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