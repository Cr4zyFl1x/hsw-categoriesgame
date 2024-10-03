package de.hsw.categoriesgame.gameapi.rpc;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Getter
@AllArgsConstructor
public class DomainData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Class<?> clazz;
    private final UUID uuid;
}
