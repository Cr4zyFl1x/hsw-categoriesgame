package de.hsw.categoriesgame.gameserver.gamelogic.util;

import java.util.Random;

public class RandomLetterUtil {

    public static char getRandomLetter() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'A');
    }
}
