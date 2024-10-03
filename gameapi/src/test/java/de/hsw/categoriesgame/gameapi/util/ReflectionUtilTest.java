package de.hsw.categoriesgame.gameapi.util;

import de.hsw.categoriesgame.gameapi.util.testres.IReflectionUtilInterfaceTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class ReflectionUtilTest {

    final Class<?> testInterface = IReflectionUtilInterfaceTest.class;



    @Test
    public void testGetGenericReturnParameter() throws NoSuchMethodException
    {
        final Method m = testInterface.getMethod("re_List_String");
        Class<?> genericClass = ReflectionUtil.getGenericReturnType(m);

        assertEquals(String.class, genericClass);
    }


    @Test
    public void testGetGenericParameterType() throws NoSuchMethodException
    {
        final Method m = testInterface.getMethod("pa_List_String__List_IReflectionUtilInterfaceTest", List.class, List.class);

        Class<?> genericClass = ReflectionUtil.getParameterGenericType(m, 0);
        assertEquals(String.class, genericClass);

        Class<?> genericClass2 = ReflectionUtil.getParameterGenericType(m, 1);
        assertEquals(IReflectionUtilInterfaceTest.class, genericClass2);
    }


    @Test
    public void testGetGenericParameterTypeMixed() throws NoSuchMethodException
    {
        final Method m = testInterface.getMethod("pa_String__List_String", String.class, List.class);
        assertThrows(RuntimeException.class, () -> ReflectionUtil.getParameterGenericType(m, 0));
    }


    @Test
    public void testGetParameterType() throws NoSuchMethodException
    {
        final Method method = testInterface.getMethod("re_String", String.class);
        final Class<?> cls  = ReflectionUtil.getMethodParameterType(method, 0);
        assertEquals(String.class, cls);

        final Method method2 = testInterface.getMethod("pa_List", List.class);
        final Class<?> cls2 = ReflectionUtil.getMethodParameterType(method2, 0);
        assertEquals(List.class, cls2);
    }


    @Test
    public void testGetReturnType() throws NoSuchMethodException
    {
        final Method method = testInterface.getMethod("re_String", String.class);
        final Class<?> cls  = ReflectionUtil.getMethodReturnType(method);
        assertEquals(String.class, cls);

        final Method method2 = testInterface.getMethod("re_List_String");
        final Class<?> cls2 = ReflectionUtil.getMethodReturnType(method2);
        assertEquals(List.class, cls2);

        final Method method3 = testInterface.getMethod("pa_List", List.class);
        final Class<?> cls3 = ReflectionUtil.getMethodReturnType(method3);
        assertEquals(void.class, cls3);
    }
}
