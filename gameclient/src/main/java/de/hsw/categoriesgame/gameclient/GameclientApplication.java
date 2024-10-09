package de.hsw.categoriesgame.gameclient;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.hsw.categoriesgame.gameapi.exception.LobbyAlreadyExistsException;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
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

    public static void main(String[] args) throws LobbyNotFoundException, InterruptedException, LobbyAlreadyExistsException {
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
        final Player player2 = new PlayerImpl("Annegret");

        //final Lobby otherLobby = game.createLobby(player);

        final Lobby lobby = game.createLobby("ABC");
        final Lobby lobby1 = game.getLobby(lobby.getLobbyCode());
        game.joinLobby(lobby.getLobbyCode(), player);
        game.joinLobby(lobby.getLobbyCode(), player1);
        //game.joinLobby(lobby.getLobbyCode(), player2);


        lobby.startGame();

        lobby.setCategories(List.of("Stadt", "Land", "Fluss"));
        lobby.startNewRound();


        System.out.println(lobby.getCategories().toString());
        System.out.println(lobby.getCurrentLetter());

        var normalAnswers = List.of(
                new NormalAnswer(player.getUUID(), "Stadt", "A1"),
                new NormalAnswer(player.getUUID(), "Land", ""),
                new NormalAnswer(player.getUUID(), "Fluss", "A1"));
        lobby.sendAnswers(normalAnswers);

        var normalAnswers1 = List.of(
                new NormalAnswer(player1.getUUID(), "Stadt", "A1"),
                new NormalAnswer(player1.getUUID(), "Land", "A2"),
                new NormalAnswer(player1.getUUID(), "Fluss", "B2"));
        lobby.sendAnswers(normalAnswers1);

        lobby.evaluateAnswers();

        System.out.println(lobby.getPointsOfPlayer(player));
        System.out.println(lobby.getPointsOfPlayer(player1));

        System.out.println(lobby.getAdmin().getName());
        game.leaveLobby(lobby.getLobbyCode(), player);
        System.out.println(lobby.getAdmin().getName());

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
