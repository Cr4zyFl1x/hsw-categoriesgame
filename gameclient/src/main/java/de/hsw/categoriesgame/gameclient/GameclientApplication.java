package de.hsw.categoriesgame.gameclient;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import javax.swing.*;
import java.awt.*;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    public static void main(String[] args) throws LobbyNotFoundException {
        System.out.println("Hello World! I'm the Client!");

        SwingUtilities.invokeLater(GameclientApplication::setupTheme);
        SwingUtilities.invokeLater(GameclientApplication::createViewManager);

        // Clientside server
        final RemoteServer socketRemoteServer = new SocketRemoteServer(new DomainRegistry());
        socketRemoteServer.start();

        // ProxyFactory
        final ConnectionDetails details = new ConnectionDetails("::1", 4703);
        final ProxyFactory factory = new RememberableProxyFactory(details, socketRemoteServer);

        // Game
        final CategorieGame game = factory.createProxy(CategorieGame.class);

        // Player
        final Player player = new PlayerImpl("Fl1x");
        final Player player1 = new PlayerImpl("Barbara");

        //final Lobby otherLobby = game.createLobby(player);

        final Lobby lobby = game.createLobby("ABC", player1);
        final Lobby lobby1 = game.joinLobby(lobby.getLobbyCode());

        lobby.startGame();

        lobby.setCategories(List.of("Stadt", "Land", "Fluss"));


        System.out.println(lobby.getCategories().toString());
        System.out.println(lobby.getCurrentLetter());

        var list = new ArrayList<String>(List.of("Angola", "Angola", "Andorra"));
        lobby.sendAnswers(list,player);
        lobby.sendAnswers(list,player1);

        lobby.evaluateAnswers();

        System.out.println(lobby.getPointsOfPlayer(player));
        System.out.println(lobby.getPointsOfPlayer(player1));


        lobby.startNewRound();

        System.out.println(lobby.getCurrentLetter());



        System.out.println(lobby.evaluateAnswers());


        System.out.println(lobby.getLobbyCode());
        //System.out.println(lobby1.hashCode());

        //System.out.println(lobby.equals(lobby1));       // TRUE
        //System.out.println(lobby.equals(otherLobby));   // FALSE

        System.out.println(lobby); // LobbyImpl@[...]

        game.leaveLobby(lobby.getLobbyCode(), player);


        //game.deleteLobby(lobby);
        //game.deleteLobby(lobby);


        //game.joinLobby(lobby.getLobbyCode(), Arrays.asList(player, player1));

        System.out.println(game.getLobbies());

        //System.out.println(game.getLobbies().get(0).getClass());

         //Should throw a LobbyNotFoundException
        //System.out.println(game.joinLobby("TEST-FAILURE", player));
    }

    /**
     * Setting up the theme for the application
     */
    private static void setupTheme() {
        FlatMacDarkLaf.setup();
        UIManager.put("Component.focusColor", Color.GRAY);
    }

    /**
     * Creating the view manager for enabling navigation between views
     */
    private static void createViewManager() {
        ViewManager viewManager = new ViewManager(createFrame());
        viewManager.changeView(View.START);
    }

    /**
     * Creating the frame for the application
     * @return  JFrame
     */
    private static JFrame createFrame() {
        JFrame frame = new JFrame();

        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("gameclient/src/main/resources/icon.png"));
        frame.setTitle("Stadt, Land, Fluss");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 700));
        frame.setVisible(true);

        return frame;
    }
}
