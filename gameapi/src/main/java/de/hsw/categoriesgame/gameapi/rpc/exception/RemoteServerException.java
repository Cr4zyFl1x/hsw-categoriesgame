package de.hsw.categoriesgame.gameapi.rpc.exception;

import lombok.experimental.StandardException;

/**
 * The {@code RemoteServerException} is thrown if there are any errors during start, connecting or control of a
 * {@code RemoteServer} instance.
 *
 * @author Florian J. Kleine-Vorholt
 */
@StandardException
public class RemoteServerException extends RuntimeException {
}
