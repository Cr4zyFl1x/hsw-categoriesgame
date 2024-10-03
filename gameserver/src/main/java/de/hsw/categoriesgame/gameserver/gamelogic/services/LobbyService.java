package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.LobbyConfigs;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;

import java.util.List;

public interface LobbyService {

    Lobby createLobby(LobbyConfigs configs);

    boolean joinLobby(String lobbyCode, Player player);

    List<Player> getAllPlayers();

    boolean leaveLobby(Player player);

    void changeAdmin();
}
