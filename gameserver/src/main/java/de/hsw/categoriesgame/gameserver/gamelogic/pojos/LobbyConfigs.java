package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

import java.util.List;

public record LobbyConfigs(
        String lobbyCode,
        int maxPlayers,
        int amountRounds,
        List<String> categories) {}
