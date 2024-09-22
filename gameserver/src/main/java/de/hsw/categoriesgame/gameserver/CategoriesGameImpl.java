package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.iface.CategoriesGame;
import de.hsw.categoriesgame.gameapi.iface.Player;
import de.hsw.categoriesgame.gameapi.lobby.Lobby;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class CategoriesGameImpl implements CategoriesGame {

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
        return null;
    }
}
