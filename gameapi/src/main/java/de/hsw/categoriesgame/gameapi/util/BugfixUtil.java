package de.hsw.categoriesgame.gameapi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class BugfixUtil {

    public static List<String> getListWithEmptyStrings(int size)
    {
        List<String> list = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            list.add("");
        }
        return list;
    }


}
