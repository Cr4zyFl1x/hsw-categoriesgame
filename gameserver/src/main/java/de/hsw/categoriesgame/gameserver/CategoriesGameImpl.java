package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

/**
 * TODO: This is only a pre dummy
 * @author Florian J. Kleine-Vorholt
 */
public class CategoriesGameImpl implements CategorieGame {

    private static final Logger log = LogManager.getLogger(CategoriesGameImpl.class);
    private final Hashtable<String, Lobby> lobbies;

    public CategoriesGameImpl() {
        lobbies = new Hashtable<>();
    }

    @Override
    public Lobby joinLobby(String lobbyCode) throws LobbyNotFoundException
    {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("No lobby found with lobbycode " + lobbyCode);
        }
        return lobbies.get(lobbyCode);
    }

    @Override
    public Lobby joinLobby(String lobbyCode, Player player) throws LobbyNotFoundException {
        System.out.println("JOINED: " + player.getName());
        return joinLobby(lobbyCode);
    }

    @Override
    public Lobby joinLobby(String lobbyCode, List<Player> player) throws LobbyNotFoundException
    {
        System.out.println(player);
        Lobby lobby = null;
        for (Player p : player) {
            lobby = joinLobby(lobbyCode, p);
        }
        return lobby;
    }

    @Override
    public Lobby createLobby()
    {
        final String lobbyCode = UUID.randomUUID().toString();
        final Lobby lobby = new LobbyImpl(lobbyCode);
        lobbies.put(lobbyCode, lobby);

        log.debug("Created lobby {}", lobbyCode);

        return lobby;
    }

    @Override
    public List<Lobby> getLobbies() {
        return lobbies.values().stream().toList();
    }

    @Override
    public void deleteLobby(String lobbyCode) throws IllegalArgumentException
    {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new IllegalArgumentException("Lobby " + lobbyCode + " does not exist!");
        }
        lobbies.remove(lobbyCode);
    }
}
