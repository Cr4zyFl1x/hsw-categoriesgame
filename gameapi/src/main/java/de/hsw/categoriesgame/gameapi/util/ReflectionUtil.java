package de.hsw.categoriesgame.gameapi.util;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class ReflectionUtil {

    private ReflectionUtil() {}



    public static Class<?> getGenericReturnType(final Method method)
    {
        final Type type = method.getGenericReturnType();

        if (!(type instanceof ParameterizedType parameterizedType))
            return null;

        if (parameterizedType.getActualTypeArguments().length == 0)
            throw new RuntimeException("Cannot get generic return type of " + method);

        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }


    public static Class<?> getParameterGenericType(final Method method, final int param)
    {
        final Type[] types = method.getGenericParameterTypes();

        if (types.length == 0) {
            throw new RuntimeException("Cannot get generic return type of " + method);
        }

        if (types.length-1 < param) {
            throw new RuntimeException("Parameter not found!");
        }

        if (!(types[param] instanceof ParameterizedType parameterizedType)) {
            throw new RuntimeException("Cannot get generic type of " + method.getName() + " parameter " + param);
        }

        return (Class<?>) parameterizedType.getActualTypeArguments()[0];
    }


    public static Class<?> getMethodReturnType(final Method method)
    {
        return method.getReturnType();
    }


    public static Class<?> getMethodParameterType(final Method method, final int param)
    {
        Class<?>[] cls = method.getParameterTypes();
        if (cls.length-1 < param) {
            throw new RuntimeException("Parameter not found!");
        }
        return cls[param];
    }
}