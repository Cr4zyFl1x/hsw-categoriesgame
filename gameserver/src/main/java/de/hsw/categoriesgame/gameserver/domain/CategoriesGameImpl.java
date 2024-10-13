package de.hsw.categoriesgame.gameserver.domain;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.exception.LobbyAlreadyExistsException;
import de.hsw.categoriesgame.gameapi.exception.LobbyFullException;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Hashtable;
import java.util.List;

public final class CategoriesGameImpl implements CategorieGame {

    /**
     * Logger
     */
    private static final Logger log = LogManager.getLogger(CategoriesGameImpl.class);

    /**
     * Map of saved/existing lobbies
     */
    private final Hashtable<String, Lobby> lobbies;



    /**
     * Constructor
     */
    public CategoriesGameImpl()
    {
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
    public Lobby joinLobby(String lobbyCode, Client client) throws LobbyNotFoundException, LobbyFullException
    {
        Lobby lobby = getLobby(lobbyCode);

        if (lobby.getPlayers().size() >= lobby.getGameConfigs().getMaxPlayers()) {
            throw new LobbyFullException("Maximum number of players reached for this Lobby!");
        }

        lobby.joinClient(client);

        // Notify
        lobby.getClients().stream()
                .filter(j -> j != client)
                .forEach(j -> j.notifyPlayerJoinLeave(lobby.getPlayers()));

        return lobby;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Lobby createLobby(String lobbyCode, GameConfigs gameConfigs) throws LobbyAlreadyExistsException {
        if (lobbies.containsKey(lobbyCode)) {
            throw new LobbyAlreadyExistsException("Lobby with code " + lobbyCode + " already exists!");
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
    public void deleteLobby(String lobbyCode) throws LobbyNotFoundException {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("Lobby " + lobbyCode + " does not exist!");
        }
        Lobby lobby = getLobby(lobbyCode);

        try {
            leaveLobby(lobby, lobby.getClients());
        } catch (UserNotInLobbyException e) {
            // Will never happen
        }

        lobbies.remove(lobbyCode);

        log.info("Deleted lobby {}", lobbyCode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteLobby(Lobby lobby) throws LobbyNotFoundException
    {
        deleteLobby(lobby.getLobbyCode());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(String lobbyCode, Client client)
            throws LobbyNotFoundException, UserNotInLobbyException
    {
        if (!lobbies.containsKey(lobbyCode)) {
            throw new LobbyNotFoundException("Lobby " + lobbyCode + " does not exist!");
        }
        var lobby = this.lobbies.get(lobbyCode);
        lobby.leaveClient(client);
        if (lobby.getPlayers().isEmpty()) {
            this.deleteLobby(lobby);
            return;
        }

        // Notify
        lobby.getClients().stream()
                .filter(j -> j != client)
                .forEach(j -> j.notifyPlayerJoinLeave(lobby.getPlayers()));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(String lobbyCode, List<Client> clients)
            throws LobbyNotFoundException, UserNotInLobbyException
    {
        for (Client client : clients) {
            leaveLobby(lobbyCode, client);
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, Client client) throws LobbyNotFoundException, UserNotInLobbyException {
        leaveLobby(lobby.getLobbyCode(), client);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveLobby(Lobby lobby, List<Client> clients) throws LobbyNotFoundException, UserNotInLobbyException {
        for (Client client : clients) {
            leaveLobby(lobby, client);
        }
    }
}