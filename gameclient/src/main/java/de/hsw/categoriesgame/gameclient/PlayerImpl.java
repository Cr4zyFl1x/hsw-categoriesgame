package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.Player;

import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class PlayerImpl implements Player {

    private final UUID uuid;
    private final String name;

    public PlayerImpl(final String name) {
        uuid = UUID.randomUUID();
        this.name = name;
    }


    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        System.out.println(name);
        return name;
    }
}