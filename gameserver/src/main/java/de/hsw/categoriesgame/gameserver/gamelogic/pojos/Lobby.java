package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

import java.util.ArrayList;
import java.util.List;

public class Lobby {
    private final String lobbyCode;
    private List<Player> players;
    private LobbyConfigs lobbyConfigs;
    private Player admin;

    public Lobby(String lobbyCode) {
        this.lobbyCode = lobbyCode;
        this.players = new ArrayList<>();
        this.lobbyConfigs = null;
    }

    public Lobby(LobbyConfigs lobbyConfigs, Player admin) {
        this.lobbyConfigs = lobbyConfigs;
        this.lobbyCode = this.lobbyConfigs.lobbyCode();
        this.players = new ArrayList<>();
        this.admin = admin;
    }


    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerByName(String name) {
        return players.stream().filter(player -> player.getName().equals(name)).findFirst().get();
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public void removePlayer(Player player) {
        this.players.remove(player);
    }

    public void setPlayerId(Player p) {
        var player = players.stream().filter(pl -> pl.getName().equals(p.getName())).toList().get(0);
        var position = players.indexOf(player);
        player.setId(position + 1);
    }

    public Player getAdmin() {
        return admin;
    }

    public void setAdmin(Player admin) {
        this.admin = admin;
    }

    public LobbyConfigs getLobbyConfigs() {
        return lobbyConfigs;
    }

    public String getLobbyCode() {
        return lobbyCode;
    }
}
