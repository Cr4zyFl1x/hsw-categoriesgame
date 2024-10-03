package de.hsw.categoriesgame.gameapi.rpc.testres;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Getter
@Setter
public class TS_Player implements ITS_Player {

    private final String name;

    public TS_Player(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TS_Player tsPlayer = (TS_Player) object;
        return Objects.equals(name, tsPlayer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}