package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 *
 * TODO:
 *
 */
public interface CategorieGame {

    Lobby joinLobby(String lobbyCode) throws LobbyNotFoundException;

    Lobby joinLobby(String lobbyCode, final Player player) throws LobbyNotFoundException;
    Lobby joinLobby(String lobbyCode, final List<Player> player) throws LobbyNotFoundException;

    Lobby createLobby();

    List<Lobby> getLobbies();

    void deleteLobby(String lobbyCode) throws IllegalArgumentException;
}
