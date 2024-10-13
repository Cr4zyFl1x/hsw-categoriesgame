package de.hsw.categoriesgame.gameapi.rpc;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface ProxyDataSerializer {

    /**
     * Serializes method arguments
     *
     * @param args  the arguments
     *
     * @return  the serialized arguments that can be transferred through an {@code ObjectStream}
     */
    Object[] serializeArguments(Object[] args);

    /**
     * Deserializes method arguments
     *
     * @param args  the arguments
     *
     * @return  the deserialized arguments that can be worked with.
     */
    Object[] deserializeArguments(Object[] args);

    /**
     * Serializes method return values
     *
     * @param returnValue   the plain return value to be serialized
     *
     * @return  return value that can be transferred through an {@code ObjectStream}
     */
    Object serializeReturnValue(Object returnValue);

    /**
     * Deserializes method return values
     *
     * @param object    the serialized method return value
     *
     * @return  the deserialized method return value that can be worked with.
     */
    Object deserializeReturnValue(Object object);
}
