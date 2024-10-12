package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.LobbyAlreadyExistsException;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
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
    public Lobby getLobby(String lobbyCode) throws LobbyNotFoundException
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
    public Lobby joinLobby(String lobbyCode, Client client) throws LobbyNotFoundException {
        Lobby lobby = getLobby(lobbyCode);
        lobby.joinClient(client);

        lobby.getClients().forEach(Client::notifyPlayerAboutLobbyState);

        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby createLobby()
    {
        final String lobbyCode = UUID.randomUUID().toString();
        final Lobby lobby = new LobbyImpl(lobbyCode, new GameConfigs(5, 10));
        lobbies.put(lobbyCode, lobby);

        log.debug("Created lobby {}", lobbyCode);

        return lobby;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby createLobby(String lobbyCode, GameConfigs gameConfigs) throws LobbyAlreadyExistsException {
        if (lobbies.containsKey(lobbyCode)) {
            throw new LobbyAlreadyExistsException();
        }
        final Lobby lobby = new LobbyImpl(lobbyCode, gameConfigs);
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
        Lobby lobby = getLobby(lobbyCode);
        leaveLobby(lobby, lobby.getClients());
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
    public void leaveLobby(String lobbyCode, Client client) throws LobbyNotFoundException {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("Lobby " + lobbyCode + " does not exist!");
        }
        var lobby = this.lobbies.get(lobbyCode);
        lobby.leaveClient(client);
        if (lobby.getPlayers().isEmpty()) {
            this.deleteLobby(lobby);
            return;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(String lobbyCode, List<Client> clients) throws LobbyNotFoundException {
        for (Client client : clients) {
            leaveLobby(lobbyCode, client);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, Client client) throws LobbyNotFoundException {
        leaveLobby(lobby.getLobbyCode(), client);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, List<Client> clients) throws LobbyNotFoundException {
        for (Client client : clients) {
            leaveLobby(lobby, client);
        }
    }
}
