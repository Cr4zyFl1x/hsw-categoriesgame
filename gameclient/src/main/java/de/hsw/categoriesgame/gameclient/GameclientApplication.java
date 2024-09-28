package de.hsw.categoriesgame.gameclient;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import javax.swing.*;
import java.awt.*;

import de.hsw.categoriesgame.gameapi.ITest;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameclientApplication {

    public static void main(String[] args) {
        System.out.println("Hello World! I'm the Client!");
        System.out.println("SharedLib works! " + ITest.class.getName());

        SwingUtilities.invokeLater(GameclientApplication::setupTheme);
        SwingUtilities.invokeLater(GameclientApplication::createViewManager);
    }

    private static void setupTheme() {
        FlatMacDarkLaf.setup();
        UIManager.put("Component.focusColor", Color.GRAY);
    }

    private static void createViewManager() {
        ViewManager viewManager = new ViewManager(createFrame());
        viewManager.changeView(View.START);
    }

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
