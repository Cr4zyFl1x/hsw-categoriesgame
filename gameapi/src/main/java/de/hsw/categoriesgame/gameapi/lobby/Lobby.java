package de.hsw.categoriesgame.gameapi.lobby;

import de.hsw.categoriesgame.gameapi.annotation.Proxyable;
import de.hsw.categoriesgame.gameapi.iface.Player;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Proxyable
public interface Lobby {

    void join(final Player player);
    void leave(final Player player);

    void setConfiguration(final LobbyConfig key, final Object value);

    void startGame(final Player player);

    void finishRound(final Player player /* ... */);

    // TODO:
    void pauseOrResumeGame(final Player player);
}
