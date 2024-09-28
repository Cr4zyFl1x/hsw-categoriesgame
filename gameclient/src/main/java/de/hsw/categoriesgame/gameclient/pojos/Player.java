package de.hsw.categoriesgame.gameclient.pojos;

/**
 * pojo class for players
 */
public class Player {
    String name;
    Integer points;
    boolean admin;

    /**
     * Constructor with one parameter
     * @param name  String
     */
    public Player(String name) {
        this.name = name;
        points = 0;
        admin = false;
    }

    /**
     * Constructor with two parameter
     * @param name      String
     * @param points    Integer
     */
    public Player(String name, Integer points) {
        this.name = name;
        this.points = points;
        admin = false;
    }

    /**
     * Constructor with three parameter
     * @param name      String
     * @param points    Integer
     * @param admin     boolean; true - player is the admin / false - player is not the admin
     */
    public Player(String name, Integer points, boolean admin) {
        this.name = name;
        this.points = points;
        this.admin = admin;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getPoints() {
        return points;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isAdmin() {
        return admin;
    }
}
