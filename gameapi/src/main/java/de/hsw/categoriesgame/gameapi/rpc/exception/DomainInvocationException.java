package de.hsw.categoriesgame.gameapi.rpc.exception;

import lombok.experimental.StandardException;

/**
 * Exception, thrown if the respective {@code DomainProvider} is unable to invoke methods because of the miss of a domain (object)
 * <p>
 *     This can also be applied if no default domain is set.
 * </p>
 *
 * @author Florian J. Kleine-Vorholt
 */
@StandardException
public class DomainInvocationException extends RuntimeException {
}