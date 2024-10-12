package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.pojo.*;
import de.hsw.categoriesgame.gameserver.gamelogic.services.Game;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class LobbyImpl implements Lobby {

    @Getter
    private final String lobbyCode;

    private final List<Client> clients;

    private Game game;

    @Getter
    private final GameConfigs gameConfiguration;

    public LobbyImpl(String lobbyCode, GameConfigs gameConfigs)
    {
        this.lobbyCode = lobbyCode;
        this.clients = new ArrayList<>();
        this.gameConfiguration = gameConfigs;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void joinClient(Client client) {
        this.clients.add(client);
        log.info("Client {} joined lobby {}!", client.getName(), lobbyCode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void leaveClient(Client client) throws UserNotInLobbyException {
        if (!this.clients.remove(client)) {
            throw new UserNotInLobbyException("User can not leave this lobby! User was not in lobby.");
        }
        log.info("Client {} left lobby {}!", client.getName(), lobbyCode);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getPointsOfPlayer(PlayerBean player)
    {
        return game.getCurrentPointsOfPlayer(map(player));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdmin(PlayerBean playerBean)
    {
        return getAdmin().equals(playerBean);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public PlayerBean getAdmin()
    {
        final Client admin = clients.stream().filter(Objects::nonNull).findFirst()
                .orElseThrow(() -> new IllegalStateException("Lobby exists but no player is existent that could be an admin!"));

        return Mapper.map(admin);
    }


    /**
     * {@inheritDoc}
     */
    public List<PlayerBean> getPlayers() {
        final List<PlayerBean> players = new ArrayList<>();
        this.clients.forEach(player -> players.add(Mapper.map(player)));
        return players;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Client> getClients() {
        return this.clients;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGameStarted()
    {
        return game != null;
    }


    // ----------------------------------------
    // ----------------------------------------


    private Client map(PlayerBean playerBean)
    {
        return clients.stream().filter(p -> p.getUUID().equals(playerBean.getUUID())).findFirst()
                .orElseThrow(() -> new IllegalStateException("This player is does not exist in lobby!"));
    }


    ///////////////////////////////////////////
    ///////////////////////////////////////////


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LobbyImpl lobby = (LobbyImpl) o;
        return Objects.equals(lobbyCode, lobby.lobbyCode);
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(lobbyCode);
    }
}