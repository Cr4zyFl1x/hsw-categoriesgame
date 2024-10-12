package de.hsw.categoriesgame.gameserver.gamelogic.util;

import java.util.Random;

/**
 * Help-Class to generate a random letter.
 */
public class RandomLetterUtil {

    /**
     * Get a random letter from A-Z.
     * @return random letter fom A-Z.
     */
    public static char getRandomLetter() {
        Random random = new Random();
        return (char) (random.nextInt(26) + 'A');
    }
}
