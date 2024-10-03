package de.hsw.categoriesgame.gameserver.gamelogic.pojos;

public class Player {
    private int id;
    private final String name;
    private int points;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int points) {
        this.name = name;
        this.points = points;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
