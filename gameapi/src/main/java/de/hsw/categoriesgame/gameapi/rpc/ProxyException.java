package de.hsw.categoriesgame.gameapi.rpc;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Getter
public final class ProxyException implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private final Class<? extends Throwable> exceptionClass;

    private final Throwable cause;

    private final String message;



    public ProxyException(Class<? extends Throwable> exceptionClass, Throwable cause, String message)
    {
        this.exceptionClass = exceptionClass;
        this.cause = cause;
        this.message = message;
    }



    public Exception buildException()
    {
        Constructor<?> constructor;
        Exception exception;
        try {
            if (cause != null && message != null) {
                constructor = exceptionClass.getConstructor(message.getClass(), Throwable.class);
                exception = (Exception) constructor.newInstance(message, cause);
            } else if (cause != null) {
                constructor = exceptionClass.getConstructor(Throwable.class);
                exception = (Exception) constructor.newInstance(cause);
            } else if (message != null) {
                constructor = exceptionClass.getConstructor(message.getClass());
                exception = (Exception) constructor.newInstance(message);
            } else {
                constructor = exceptionClass.getConstructor();
                exception = (Exception) constructor.newInstance();
            }
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            return new RuntimeException(e);
        }
        return exception;
    }
}