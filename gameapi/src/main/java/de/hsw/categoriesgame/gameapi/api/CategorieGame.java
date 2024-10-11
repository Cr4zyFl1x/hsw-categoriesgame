package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.exception.LobbyAlreadyExistsException;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
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
     * @param player    player has to be initialized beforehand
     * @return          object of class Lobby that the user joined
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    Lobby joinLobby(String lobbyCode, final Player player) throws LobbyNotFoundException;

    /**
     * Create lobby with random UUID lobby code.
     * @return  object of class lobby that was created
     */
    Lobby createLobby();

    /**
     * Create lobby with specified unique lobby code.
     * @param lobbyCode unique string that identifies the lobby
     * @return          object of class lobby that was created
     */
    Lobby createLobby(String lobbyCode, GameConfigs gameConfigs) throws LobbyAlreadyExistsException;

    /**
     * Get all created lobbies.
     * @return  list of all created lobbies
     */
    List<Lobby> getLobbies();

    /**
     * Deletes specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @throws LobbyNotFoundException   if lobby is not found (e.g. not valid lobbyCode)
     */
    void deleteLobby(String lobbyCode) throws LobbyNotFoundException;

    /**
     * Deletes specified lobby.
     * @param lobby object of class Lobby that should be deleted
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    void deleteLobby(Lobby lobby) throws LobbyNotFoundException;

    /**
     * Specified player leaves the specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @param player    object of class Player
     * @throws LobbyNotFoundException if lobby is not found (e.g. not valid lobbyCode)
     */
    void leaveLobby(String lobbyCode, Player player) throws LobbyNotFoundException;

    /**
     * List of players leave the specified lobby.
     * @param lobbyCode string that identifies the lobby
     * @param players   list of players
     * @throws LobbyNotFoundException   if lobby is not found (e.g. not valid lobbyCode)
     */
    void leaveLobby(String lobbyCode, List<Player> players) throws LobbyNotFoundException;

    /**
     * Specified player leaves the specified lobby.
     * @param lobby     object of class Lobby
     * @param player    object of class Player
     */
    void leaveLobby(Lobby lobby, Player player) throws LobbyNotFoundException;

    /**
     * Specified list of players leave the specified lobby.
     * @param lobby     object of class Lobby
     * @param players   list of players
     */
    void leaveLobby(Lobby lobby, List<Player> players) throws LobbyNotFoundException;
}
