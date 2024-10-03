package de.hsw.categoriesgame.gameapi.rpc.testres;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class TS_Game implements ITS_Game {

    private Hashtable<UUID, ITS_Lobby> lobbies = new Hashtable<>();


    @Override
    public ITS_Lobby createLobby()
    {
        final UUID lobbyCode = UUID.randomUUID();
        final ITS_Lobby lobby = new TS_Lobby(lobbyCode);
        lobbies.put(lobbyCode, lobby);

        return lobby;
    }

    @Override
    public ITS_Lobby joinLobby(ITS_Lobby lobby, ITS_Player player)
    {
        lobby.join(player);
        return lobby;
    }

    @Override
    public ITS_Lobby joinLobby(ITS_Lobby lobby, List<ITS_Player> player) {
        for (ITS_Player p : player) {
            lobby.join(p);
        }
        return lobby;
    }

    @Override
    public ITS_Lobby getLobby(String lobbyCode) {
        return lobbies.get(UUID.fromString(lobbyCode));
    }

    @Override
    public List<ITS_Lobby> getLobbies()
    {
        return lobbies.values().stream().toList();
    }

    @Override
    public void deleteLobby(ITS_Lobby lobby) throws TS_CustomException {

        if (!lobbies.containsKey(UUID.fromString(lobby.getLobbyCode()))) {
            throw new TS_CustomException("Lobby not found");
        }
        lobbies.remove(UUID.fromString(lobby.getLobbyCode()));
    }

    @Override
    public int getLobbyCount() {
        return lobbies.size();
    }
}