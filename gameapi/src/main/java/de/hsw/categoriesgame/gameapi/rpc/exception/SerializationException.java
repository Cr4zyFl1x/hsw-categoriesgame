package de.hsw.categoriesgame.gameapi.rpc.exception;

import lombok.experimental.StandardException;

/**
 * Thrown during (de-)serialization
 *
 * @author Florian J. Kleine-Vorholt
 */
@StandardException
public class SerializationException extends RuntimeException {
}