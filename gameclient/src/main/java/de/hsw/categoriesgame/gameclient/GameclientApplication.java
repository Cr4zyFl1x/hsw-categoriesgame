package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.ITest;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    public static void main(String[] args) {
        System.out.println("Hello World! I'm the Client!");
        System.out.println("SharedLib works! " + ITest.class.getName());
    }

}
