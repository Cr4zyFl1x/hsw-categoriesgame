package de.hsw.categoriesgame.gameapi.iface;

import de.hsw.categoriesgame.gameapi.lobby.Lobby;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface CategoriesGame {

    public Lobby createLobby(final Player player);

    public Lobby joinLobby(final Player player, final String lobbyCode);
}