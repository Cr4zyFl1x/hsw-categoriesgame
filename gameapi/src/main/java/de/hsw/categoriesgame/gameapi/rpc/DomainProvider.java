package de.hsw.categoriesgame.gameapi.rpc;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface DomainProvider extends Runnable {

    /**
     * Gets the domain of the provider
     */
    Object getDomain();
}
