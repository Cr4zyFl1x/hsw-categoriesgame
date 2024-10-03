package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.impl.SocketRemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyFactoryRegistry;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.ProxyRegistry;

import java.net.UnknownHostException;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    public static void main(String[] args) throws UnknownHostException, LobbyNotFoundException {

        // Clientside server
        final RemoteServer socketRemoteServer = new SocketRemoteServer(new DomainRegistry(), null);
        socketRemoteServer.start();

        final ProxyFactoryRegistry factoryReg = ProxyFactoryRegistry.getRegistry();

        // ProxyFactory
        final ConnectionDetails details = new ConnectionDetails("::1", 4703);
        final ProxyFactory factory = new RememberableProxyFactory(details, socketRemoteServer);

        // Game
        final CategorieGame game = factory.createProxy(CategorieGame.class);

        // Player
        final Player player = new PlayerImpl("Fl1x");
        final Player player1 = new PlayerImpl("Barbara");

        final Lobby otherLobby = game.createLobby();

        final Lobby lobby = game.createLobby();
        final Lobby lobby1 = game.joinLobby(lobby.getLobbyCode());

        System.out.println(lobby.hashCode());
        System.out.println(lobby1.hashCode());

        System.out.println(lobby.equals(lobby1));       // TRUE
        System.out.println(lobby.equals(otherLobby));   // FALSE

        System.out.println(lobby); // LobbyImpl@[...]


        game.deleteLobby(lobby);
        game.deleteLobby(lobby);


//        game.joinLobby(lobby.getLobbyCode(), List.of(player, player1));
//
//        System.out.println(game.getLobbies());
//
//        System.out.println(game.getLobbies().get(0).getClass());

        // Should throw a LobbyNotFoundException
//        System.out.println(game.joinLobby("TEST-FAILURE", player));
    }
}