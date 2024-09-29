package de.hsw.categoriesgame.gameapi.rpc;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyDataSerializer {

    Object[] serializeArguments(Object[] args);

    Object serializeReturnValue(Object returnValue);

    Object deserializeReturnValue(Object object);
}
