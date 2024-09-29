package de.hsw.categoriesgame.gameapi.rpc;

import java.lang.reflect.Method;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyDataSerializer {

    Object[] serializeArguments(Object[] args, Method method);

    Object[] deserializeArguments(Object[] args);

    Object serializeReturnValue(Object returnValue, Method method);

    Object deserializeReturnValue(Object object);
}
