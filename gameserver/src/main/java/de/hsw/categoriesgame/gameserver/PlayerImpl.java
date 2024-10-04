package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.api.Player;

import java.util.List;
import java.util.UUID;

public class PlayerImpl implements Player {

    private int id;

    private UUID uuid;
    private final String name;

    public PlayerImpl(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return name;
    }

    //-----------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final PlayerImpl other = (PlayerImpl) obj;
        return this.name.equals(other.name);
    }
}
