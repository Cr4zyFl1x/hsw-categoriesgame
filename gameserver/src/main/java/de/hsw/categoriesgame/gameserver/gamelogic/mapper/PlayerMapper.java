package de.hsw.categoriesgame.gameserver.gamelogic.mapper;

import de.hsw.categoriesgame.gameserver.PlayerImpl;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;

public class PlayerMapper {

    public static PlayerImpl map(de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player playerPojo) {
        PlayerImpl playerImpl = new PlayerImpl(playerPojo.getName());
        playerImpl.setId(playerPojo.getId());
        return playerImpl;
    }

    public static Player map(PlayerImpl playerImpl) {
        Player player = new Player(playerImpl.getName());
        player.setId(playerImpl.getId());
        return player;
    }
}
