package de.hsw.categoriesgame.gameapi.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class ReflectionUtil {

    public static Class<?> getGenericReturnType(final Method method)
    {
        final Type type = method.getGenericReturnType();

        if (!(type instanceof ParameterizedType parameterizedType))
            return null;

        if (parameterizedType.getActualTypeArguments().length == 0)
            throw new RuntimeException("Cannot get generic return type of " + method);

        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }

}
