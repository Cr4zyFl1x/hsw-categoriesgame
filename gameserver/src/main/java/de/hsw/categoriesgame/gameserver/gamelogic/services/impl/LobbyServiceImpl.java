package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.LobbyConfigs;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.services.LobbyService;

import java.util.List;

public class LobbyServiceImpl implements LobbyService {

    private LobbyConfigs lobbyConfigs;

    private Lobby lobby;

    public LobbyServiceImpl() { }

    public LobbyServiceImpl(LobbyConfigs lobbyConfigs) {
        this.lobbyConfigs = lobbyConfigs;
    }

    @Override
    public Lobby createLobby(LobbyConfigs configs) {
        return null;
    }

    @Override
    public boolean joinLobby(String lobbyCode, Player player) {
        return false;
    }

    @Override
    public List<Player> getAllPlayers() {
        return List.of();
    }

    @Override
    public boolean leaveLobby(Player player) {
        return false;
    }

    public Player getCurrentAdmin() {
        return null;
    }

    @Override
    public void changeAdmin() {

    }

    public Lobby getLobby() {
        return lobby;
    }
}
