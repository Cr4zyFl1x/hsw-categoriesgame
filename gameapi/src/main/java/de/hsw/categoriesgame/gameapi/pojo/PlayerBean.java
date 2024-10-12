package de.hsw.categoriesgame.gameapi.pojo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * pojo class for players
 */
public class PlayerBean implements Serializable {

    private final UUID uuid;

    @Getter
    private final String name;

    @Getter
    @Setter
    private Integer points;

    /**
     * Constructor with one parameter
     * @param name  String
     */
    public PlayerBean(final String name) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.points = 0;
    }

    public PlayerBean(final UUID uuid, final String name) {
        this.uuid = uuid;
        this.name = name;
        points = 0;
    }


    /**
     * Gets the UUID of the player
     * @return the uuid
     */
    public UUID getUUID() {
        return uuid;
    }


    ///////////////////////////////////////
    ///////////////////////////////////////


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PlayerBean playerBean = (PlayerBean) object;
        return Objects.equals(uuid, playerBean.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }
}