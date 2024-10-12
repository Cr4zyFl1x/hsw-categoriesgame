package de.hsw.categoriesgame.gameclient;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import javax.swing.*;
import java.awt.*;

import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.SocketRemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    private static final Logger log = LoggerFactory.getLogger(GameclientApplication.class);

    @Getter
    private static PropertyResourceBundle config;

    private static final RemoteServer mySideServer;

    @Getter
    private static ProxyFactory proxyFactory;

    @Getter
    private static CategorieGame remoteGame;

    static {
        mySideServer = new SocketRemoteServer(new DomainRegistry());
    }

    public static void main(String[] args)
    {
        readConfig();
        setupServerAndProxyFactory();

        SwingUtilities.invokeLater(GameclientApplication::setupTheme);
        SwingUtilities.invokeLater(GameclientApplication::createViewManager);
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
        ViewManager viewManager = new ViewManager(createFrame(), getProxyFactory());
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


    private static void setupServerAndProxyFactory()
    {
        mySideServer.start();

        final ConnectionDetails connectionDetails = new ConnectionDetails(
                getConfig().getString("remote-server.hostname"),
                Integer.parseInt(getConfig().getString("remote-server.port"))
        );
        proxyFactory = new RememberableProxyFactory(connectionDetails, mySideServer);
        remoteGame = proxyFactory.createProxy(CategorieGame.class);
    }


    private static void readConfig()
    {
        try (InputStream inputStream = GameclientApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Could not find application.properties");
            }
            config = new PropertyResourceBundle(inputStream);
        } catch (Exception e) {
            log.error("Error reading application.properties", e);
            System.exit(1);
        }
    }
}