package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.iface.CategoriesGame;
import de.hsw.categoriesgame.gameapi.iface.Player;
import de.hsw.categoriesgame.gameapi.lobby.Lobby;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class CategoriesGameImpl implements CategoriesGame {

    private static final Logger log = LogManager.getLogger(CategoriesGameImpl.class);
    private Hashtable<String, Lobby> lobbies;

    public CategoriesGameImpl()
    {
        lobbies = new Hashtable<>();
    }

    @Override
    public Lobby createLobby(Player player) {
        return null;
    }

    @Override
    public Lobby joinLobby(Player player, String lobbyCode) {

        log.info("Joining lobby " + lobbyCode);

        System.out.println(player);
        return null;
    }

    // X4F2
}
