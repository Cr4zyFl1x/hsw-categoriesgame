package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.LobbyConfigs;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.LobbyServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LobbyServiceTest {

    private static LobbyServiceImpl lobbyService;

    @BeforeAll
    static void setup() {
        var lobbyConfigs = new LobbyConfigs(
                "ABC",
                10,
                3,
                List.of("Stadt", "Land", "Name"));
        lobbyService = new LobbyServiceImpl(lobbyConfigs, new Player("A"));
    }

    @Test
    void createLobby() {
        var lobbyConfigs = new LobbyConfigs(
                "ABC",
                10,
                3,
                List.of("Stadt", "Land", "Name"));
        var lobby = lobbyService.createLobby(lobbyConfigs, new Player("A"));

        assertEquals("ABC", lobby.getLobbyCode());
    }

    @Test
    void joinLobby() {
        lobbyService.joinLobby("ABC", new Player("A"));

        assertTrue(lobbyService.getLobby().getPlayers().stream()
                .map(Player::getName).toList()
                .contains("A"));
    }

    @Test
    void leaveLobby() {
        lobbyService.leaveLobby(new Player("A"));

        assertFalse(lobbyService.getLobby().getPlayers().stream()
                .map(Player::getName).toList()
                .contains("A"));
    }

    @Test
    void changeAdmin() {
        lobbyService.getLobby().addPlayer(new Player("B"));
        var oldAdmin = lobbyService.getCurrentAdmin();
        lobbyService.changeAdmin();
        var newAdmin = lobbyService.getCurrentAdmin();

        assertNotEquals(oldAdmin, newAdmin);
    }

}