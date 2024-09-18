package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.ITest;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameserverApplication {

    public static void main(String[] args) {
        System.out.println("Hello World! I'm the Server!");
        System.out.println("SharedLib works! " + ITest.class.getName());
    }
}
