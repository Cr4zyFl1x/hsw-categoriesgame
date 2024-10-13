package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.pojo.*;

import java.util.List;

public interface Lobby {

    /**
     * Get unique lobby code that identifies the lobby.
     * @return  lobby code
     */
    String getLobbyCode();


    /**
     * Adds player to lobby.
     * @param client    object of class Player
     */
    void joinClient(Client client);

    /**
     * Removes player from lobby.
     * @param client    object of class Player
     */
    void leaveClient(Client client) throws UserNotInLobbyException;


    /**
     * Checks if the given player is an administrator
     *
     * @param playerBean    the player dto
     * @return              true if is administrator
     */
    boolean isAdmin(PlayerBean playerBean);


    /**
     * Gets the administrator player
     *
     * @return  the administrative player
     */
    PlayerBean getAdmin();


    /**
     * Gets a list of players of this lobby
     * @return List of players
     */
    List<PlayerBean> getPlayers();


    /**
     * Returns a list of remote Clients ("Players")
     * @return List of clients in this lobby
     */
    List<Client> getClients();


    /**
     * Gets the game configuration
     *
     * @return The game configuration
     */
    GameConfigs getGameConfigs();

    /**
     * Checks if game has started
     *
     * @return true if game has started
     */
    boolean hasGameStarted();


    /**
     * Starts the game
     */
    void startGame();


    /**
     *
     */
    void startRound();

    /**
     * Receives a {@link PlayerResult} POJO containing the answers for this round
     * @param playerResult  the result
     */
    void receivePlayerAnswer(final PlayerResult playerResult);


    /**
     * Gets the Results of the current round
     * @return
     */
    RoundResults getCurrentRoundResults();

    List<PlayerBean> getActualPlayers();
}