package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.PlayerResult;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameserver.gamelogic.ServerGame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class LobbyImpl implements Lobby {

    /**
     * The code of the lobby
     */
    @Getter
    private final String lobbyCode;

    /**
     * Clients in this lobby
     */
    private final List<Client> clients;

    /**
     * The "core" game
     */
    private ServerGame game;

    /**
     * Game configuration used to start the game
     */
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
    public GameConfigs getGameConfigs()
    {
        return this.gameConfiguration;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasGameStarted()
    {
        return game != null && game.isStarted();
    }


    /**
     * Creates if necessary and starts it if not yet started
     */
    @Override
    public void startGame() {
        if (game == null) {
            game = new ServerGame(getClients(), getGameConfiguration());
        }
        if (!game.isStarted()) {
            game.startGame();
            return;
        }
        throw new IllegalStateException("Game already started!");
    }


    /**
     * Starts a new round
     */
    @Override
    public void startRound()
    {
        if (!hasGameStarted()) {
            throw new IllegalStateException("Game hat not yet started!");
        }
        game.startRound();
    }



    @Override
    public void receivePlayerAnswer(PlayerResult playerResult) {
        if (playerResult == null)
            throw new IllegalArgumentException("Player result is null!");

        if (!hasGameStarted()) {
            throw new IllegalStateException("Game has not yet started! Therefore no answers can be sent!");
        }
        game.receivePlayerAnswer(playerResult);
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