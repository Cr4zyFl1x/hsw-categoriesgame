package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

import java.io.Serializable;

public class Player implements Serializable {
    private int id;
    private final String name;

    private boolean hasAnswered;

    public Player(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public boolean hasAnswered() {
        return hasAnswered;
    }

    public void setHasAnswered(boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }

    //-----------------------
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Player other = (Player) obj;
        return this.name.equals(other.name);
    }
}
