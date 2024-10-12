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
     * Gets the current points of a player
     * @param player
     * @return
     */
    int getPointsOfPlayer(PlayerBean player);


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
     * Checks if game has started
     *
     * @return true if game has started
     */
    boolean hasGameStarted();
}