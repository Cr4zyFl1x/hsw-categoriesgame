package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Lobby;

import java.util.Objects;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class LobbyImpl implements Lobby {

    private final String lobbyCode;

    public LobbyImpl(String lobbyCode)
    {
        this.lobbyCode = lobbyCode;
    }


    @Override
    public String getLobbyCode()
    {
        return lobbyCode;
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