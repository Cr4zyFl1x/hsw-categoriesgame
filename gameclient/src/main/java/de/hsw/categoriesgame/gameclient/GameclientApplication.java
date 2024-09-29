package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    public static void main(String[] args) {



        final ConnectionDetails details = new ConnectionDetails("::1", 4703);
        final ProxyFactory factory = new RememberableProxyFactory(details);

        final CategorieGame game = factory.createProxy(CategorieGame.class);

        System.out.println(game.getLobbies());

        String s = game.createLobby().getLobbyCode();

        System.out.println(game.getLobbies());

        game.deleteLobby(s);

        try {
            game.deleteLobby(s);
        } catch (IllegalArgumentException e) {
            System.out.println("dededed");
            e.printStackTrace();
        }

        System.out.println(s);
    }

}
