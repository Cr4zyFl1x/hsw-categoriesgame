package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

import java.util.List;

public class Lobby {
    private final String lobbyCode;
    private List<Player> players;
    private Game game;
    private LobbyConfigs lobbyConfigs;
    private Player admin;

    public Lobby(String lobbyCode) {
        this.lobbyCode = lobbyCode;
    }

    public Lobby(LobbyConfigs lobbyConfigs) {
        this.lobbyConfigs = lobbyConfigs;
        this.lobbyCode = this.lobbyConfigs.lobbyCode();
    }


    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
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
