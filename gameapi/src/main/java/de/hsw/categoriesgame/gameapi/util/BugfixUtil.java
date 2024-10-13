package de.hsw.categoriesgame.gameapi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class BugfixUtil {

    /**
     * Creates a list with a defined number of empty strings
     * @param size  number of empty strings
     * @return  list with empty strings
     */
    public static List<String> getListWithEmptyStrings(int size)
    {
        List<String> list = new ArrayList<String>(size);
        for (int i = 0; i < size; i++) {
            list.add("");
        }
        return list;
    }
}