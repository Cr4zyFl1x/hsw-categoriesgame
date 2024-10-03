package de.hsw.categoriesgame.gameapi.rpc.testres;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ITS_Game {

    ITS_Lobby createLobby();
    ITS_Lobby joinLobby(ITS_Lobby lobby, ITS_Player player);
    ITS_Lobby joinLobby(ITS_Lobby lobby, List<ITS_Player> player);

    ITS_Lobby getLobby(String lobbyCode);

    List<ITS_Lobby> getLobbies();

    void deleteLobby(ITS_Lobby lobby) throws TS_CustomException;

    int getLobbyCount();
}
