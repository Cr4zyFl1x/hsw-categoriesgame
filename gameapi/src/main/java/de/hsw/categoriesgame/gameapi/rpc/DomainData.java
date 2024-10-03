package de.hsw.categoriesgame.gameapi.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * Serializable POJO for transporting domain information
 *
 * @author Florian J. Kleine-Vorholt
 */
@Getter
@AllArgsConstructor
public class DomainData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Type of the domain (Interface)
     */
    private final Class<?> clazz;

    /**
     * UUID on the server of the domain
     */
    private final UUID uuid;
}
