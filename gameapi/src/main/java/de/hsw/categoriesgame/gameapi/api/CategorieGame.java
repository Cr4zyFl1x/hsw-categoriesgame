package de.hsw.categoriesgame.gameapi.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface CategorieGame {

    Lobby joinLobby(String lobbyCode);

    Lobby createLobby();

    List<Lobby> getLobbies();

    void deleteLobby(String lobbyCode) throws IllegalArgumentException;
}
