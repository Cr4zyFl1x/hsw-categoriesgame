package de.hsw.categoriesgame.gameapi.rpc.testres;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ITS_Lobby {

    String getLobbyCode();

    void join(ITS_Player player);

    List<ITS_Player> getPlayers();

    ITS_Player getAdmin() throws TS_CustomException;
}