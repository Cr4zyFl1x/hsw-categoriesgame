package de.hsw.categoriesgame.gameapi.rpc.testres;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class TS_Lobby implements ITS_Lobby {

    private final UUID lobbyCode;

    private final List<ITS_Player> players = new ArrayList<>();



    public TS_Lobby(UUID lobbyCode)
    {
        this.lobbyCode = lobbyCode;
    }



    @Override
    public String getLobbyCode()
    {
        return this.lobbyCode.toString();
    }

    @Override
    public void join(ITS_Player player)
    {
        if (players.contains(player)) {
            throw new TS_CustomException("Player already joined");
        }
        players.add(player);
    }

    @Override
    public List<ITS_Player> getPlayers()
    {
        return players;
    }

    @Override
    public ITS_Player getAdmin() throws TS_CustomException {

        if (players.isEmpty()) {
            throw new TS_CustomException("No Admin set!");
        }
        return players.get(0);
    }
}