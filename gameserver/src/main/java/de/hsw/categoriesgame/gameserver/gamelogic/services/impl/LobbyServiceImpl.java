package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.LobbyConfigs;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.services.LobbyService;

import java.util.List;

public class LobbyServiceImpl implements LobbyService {

    private LobbyConfigs lobbyConfigs;
    private Lobby lobby;

    public LobbyServiceImpl(LobbyConfigs lobbyConfigs) {
        this.lobbyConfigs = lobbyConfigs;
        this.lobby = new Lobby(lobbyConfigs, null);
    }

    public LobbyServiceImpl(LobbyConfigs lobbyConfigs, Player admin) {
        this.lobbyConfigs = lobbyConfigs;
        this.lobby = new Lobby(lobbyConfigs, admin);
    }

    @Override
    public Lobby createLobby(LobbyConfigs configs, Player player) {
        if (this.lobbyConfigs == null) {
            this.lobbyConfigs = configs;
        }
        var lobby = new Lobby(configs, player);
        this.lobby = lobby;
        return lobby;
    }

    @Override
    public boolean joinLobby(String lobbyCode, Player player) {
        this.lobby.addPlayer(player);
        return false;
    }

    @Override
    public List<Player> getAllPlayers() {
        return lobby.getPlayers();
    }

    @Override
    public boolean leaveLobby(Player player) {
        this.lobby.removePlayer(player);
        return true;
    }

    @Override
    public void changeAdmin() {
        if (!this.lobby.getPlayers().isEmpty()) {
            this.lobby.setAdmin(this.lobby.getPlayers().get(0));
        }
    }

    public Lobby getLobby() {
        return this.lobby;
    }

    public Player getCurrentAdmin() {
        return this.lobby.getAdmin();
    }
}
