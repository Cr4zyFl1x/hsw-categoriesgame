package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.exception.LobbyAlreadyExistsException;
import de.hsw.categoriesgame.gameapi.exception.LobbyFullException;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;

import java.util.List;

public interface CategorieGame {

    /**
     * Player joins the specified lobby.
     * @param   lobbyCode   string that is specified when creating a lobby
     * @return  object of class Lobby that the user joined
     * @throws  LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    Lobby getLobby(String lobbyCode) throws LobbyNotFoundException;


    /**
     * Specified player joins a specified lobby.
     * @param lobbyCode string that is specified when creating a lobby
     * @param client    player has to be initialized beforehand
     * @return          object of class Lobby that the user joined
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    Lobby joinLobby(String lobbyCode, final Client client) throws LobbyNotFoundException, LobbyFullException;


    /**
     * Create lobby with specified unique lobby code.
     * @param lobbyCode unique string that identifies the lobby
     * @return          object of class lobby that was created
     */
    Lobby createLobby(String lobbyCode, GameConfigs gameConfigs) throws LobbyAlreadyExistsException;


    /**
     * Deletes specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @throws LobbyNotFoundException   if lobby is not found (e.g. not valid lobbyCode)
     */
    void deleteLobby(String lobbyCode) throws LobbyNotFoundException, UserNotInLobbyException;


    /**
     * Deletes specified lobby.
     * @param lobby object of class Lobby that should be deleted
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    void deleteLobby(Lobby lobby) throws LobbyNotFoundException;


    /**
     * Specified player leaves the specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @param client    object of class Player
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    void leaveLobby(String lobbyCode, Client client) throws LobbyNotFoundException, UserNotInLobbyException;


    /**
     * List of players leave the specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @param clients   list of players
     * @throws LobbyNotFoundException   if lobby is not found (e.g. not valid lobbyCode)
     */
    void leaveLobby(String lobbyCode, List<Client> clients) throws LobbyNotFoundException, UserNotInLobbyException;


    /**
     * Specified player leaves the specified lobby.
     * @param lobby     object of class Lobby
     * @param client    object of class Player
     */
    void leaveLobby(Lobby lobby, Client client) throws LobbyNotFoundException, UserNotInLobbyException;


    /**
     * Specified list of players leave the specified lobby.
     * @param lobby     object of class Lobby
     * @param clients   list of players
     */
    void leaveLobby(Lobby lobby, List<Client> clients) throws LobbyNotFoundException, UserNotInLobbyException;
}