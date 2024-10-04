package de.hsw.categoriesgame.gameserver.gamelogic.util;

import java.util.List;

public class SortingUtil {

    private SortingUtil() {}

    public static List<List<Integer>> sortResults(List<List<Integer>> list) {
        return list.stream()
                .sorted((l1, l2) -> l2.get(1).compareTo(l1.get(1)))
                .toList();
    }
}
