package de.hsw.categoriesgame.gameapi.util;

import java.util.Random;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RandomStringUtil {

    public enum Type {
        CAPITAL_DIGITS  ("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"),
        DIGITS          ("0123456789"),
        CAPITAL         ("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

        private final String str;
        Type(String letters) {
            str = letters;
        }

        public String getLetters() {
            return str;
        }
    }


    public static String generate(final Type type, final int length)
    {
        final StringBuilder result = new StringBuilder(length);
        final Random random = new Random();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(type.getLetters().length());
            result.append(type.getLetters().charAt(index));
        }

        return result.toString();
    }


}