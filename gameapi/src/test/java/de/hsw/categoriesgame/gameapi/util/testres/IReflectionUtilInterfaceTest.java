package de.hsw.categoriesgame.gameapi.util.testres;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public interface IReflectionUtilInterfaceTest {

    List<String> re_List_String();
    String re_String(final String string);

    void pa_List(List<String> param);
    void pa_List_String__List_IReflectionUtilInterfaceTest(List<String> param, List<IReflectionUtilInterfaceTest> list);
    void pa_String__List_String(final String blabla, List<String> param);
}