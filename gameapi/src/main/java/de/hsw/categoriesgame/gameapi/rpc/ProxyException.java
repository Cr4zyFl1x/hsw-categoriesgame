package de.hsw.categoriesgame.gameapi.rpc;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Serializable POJO for transporting Exception details
 *
 * @author Florian J. Kleine-Vorholt
 */
@Getter
public final class ProxyException implements Serializable {

    @Serial
    private static final long serialVersionUID = 2L;

    private static final Logger log = LoggerFactory.getLogger(ProxyException.class);

    /**
     * Type of the exception
     */
    private final Class<? extends Throwable> exceptionClass;

    /**
     * Cause of the exception
     */
    private final Throwable cause;

    /**
     * Message information of the exception
     */
    private final String message;


    /**
     * Creates a new ProxyException
     *
     * @param exceptionClass    the class of the exception
     * @param cause             the cause of the exception
     * @param message           the message of the exception
     */
    public ProxyException(final Class<? extends Throwable> exceptionClass,
                          final Throwable cause, String message)
    {
        this.exceptionClass = exceptionClass;
        this.cause = cause;
        this.message = message;
    }

    /**
     * Creates a new ProxyException
     *
     * @param exceptionClass    the class of the exception
     * @param message           the message of the exception
     */
    public ProxyException(final Class<? extends Throwable> exceptionClass, final String message)
    {
        this(exceptionClass, null, message);
    }

    /**
     * Creates a new ProxyException
     *
     * @param exceptionClass    the class of the exception
     * @param cause             the cause of the exception
     */
    public ProxyException(final Class<? extends Throwable> exceptionClass, final Throwable cause)
    {
        this(exceptionClass, cause, null);
    }

    /**
     * Creates a new ProxyException
     *
     * @param exceptionClass    the class of the exception
     */
    public ProxyException(final Class<? extends Throwable> exceptionClass)
    {
        this(exceptionClass, null, null);
    }


    /**
     * Builds an instance of the exception by the details of this {@link ProxyException}
     *
     * @return the Exception
     */
    public Exception buildException()
    {
        Constructor<?> constructor;
        Exception exception;
        log.trace(String.valueOf(exceptionClass));
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