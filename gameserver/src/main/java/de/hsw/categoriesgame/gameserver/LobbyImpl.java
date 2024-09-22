package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.iface.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class LobbyImpl {

    private final String lobbyCode;
    private final Set<Player> players;


    public LobbyImpl(String lobbyCode, final Player admin)
    {
        this.lobbyCode = lobbyCode;
        this.players = new HashSet<>();
        this.players.add(admin);
    }



}