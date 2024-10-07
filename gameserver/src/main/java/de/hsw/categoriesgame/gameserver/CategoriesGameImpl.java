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

public class CategoriesGameImpl implements CategorieGame {

    private static final Logger log = LogManager.getLogger(CategoriesGameImpl.class);
    private final Hashtable<String, Lobby> lobbies;

    public CategoriesGameImpl() {
        lobbies = new Hashtable<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby joinLobby(String lobbyCode) throws LobbyNotFoundException
    {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("No lobby found with lobbycode " + lobbyCode);
        }
        return lobbies.get(lobbyCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby joinLobby(String lobbyCode, Player player) throws LobbyNotFoundException {
        log.info("JOINED: " + player.getName());
        Lobby lobby = joinLobby(lobbyCode);
        lobby.addPlayer(player);
        if (lobby.getAdmin() == null) {
            lobby.setAdmin(player);
        }
        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby joinLobby(String lobbyCode, List<Player> players) throws LobbyNotFoundException
    {
        log.debug(players);
        Lobby lobby = null;
        for (Player p : players) {
            lobby = joinLobby(lobbyCode, p);
        }
        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby createLobby()
    {
        final String lobbyCode = UUID.randomUUID().toString();
        final Lobby lobby = new LobbyImpl(lobbyCode);
        lobbies.put(lobbyCode, lobby);

        log.debug("Created lobby {}", lobbyCode);

        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby createLobby(String lobbyCode) {
        final Lobby lobby = new LobbyImpl(lobbyCode);
        lobbies.put(lobbyCode, lobby);

        log.debug("Created lobby {}", lobbyCode);

        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Lobby> getLobbies() {
        return lobbies.values().stream().toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLobby(String lobbyCode) throws LobbyNotFoundException
    {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("Lobby " + lobbyCode + " does not exist!");
        }
        lobbies.remove(lobbyCode);

        log.info("Deleted lobby {}", lobbyCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLobby(Lobby lobby) throws LobbyNotFoundException {
        deleteLobby(lobby.getLobbyCode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(String lobbyCode, Player player) throws LobbyNotFoundException {
        log.info("LEAVING: " + player.getName());
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("Lobby " + lobbyCode + " does not exist!");
        }
        var lobby = this.lobbies.get(lobbyCode);
        lobby.removePlayer(player);
        if (lobby.getAdmin().equals(player)) {
            lobby.changeAdmin();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(String lobbyCode, List<Player> players) throws LobbyNotFoundException {
        for (Player player : players) {
            leaveLobby(lobbyCode, player);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, Player player) {
        lobby.removePlayer(player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, List<Player> players) {
        for (Player player : players) {
            leaveLobby(lobby, player);
        }
    }
}
