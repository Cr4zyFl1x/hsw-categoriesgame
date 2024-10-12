package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class LobbyWaitingView extends JPanel implements InitializableView {

    private JLabel waitingHeader;
    private JButton leaveButton;
    private JButton startGameButton;
    private JPanel buttonPanel;
    private JPanel playerPanel;
    private List<JLabel> playerLabels = new ArrayList<>();

    public LobbyWaitingView() {
        initializeComponents();
        buildView();
    }

    public JLabel getWaitingHeader() {
        return waitingHeader;
    }

    public JButton getLeaveButton() {
        return leaveButton;
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    @Override
    public void initializeComponents() {
        // Header
        waitingHeader = new JLabel("Waiting for the game...");
        waitingHeader.setFont(new Font("Arial", Font.BOLD, 24));

        // leave button
        leaveButton = new JButton("Leave");

        // Start game button
        startGameButton = new JButton("Start Game");
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setBackground(new Color(25, 175, 50));
        startGameButton.setOpaque(false);
        startGameButton.setBorderPainted(false);

        // Panel for the buttons and players
        buttonPanel = new JPanel();
        playerPanel = new JPanel();
    }

    @Override
    public void buildView() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(waitingHeader, gbc);

        gbc.gridy = 1;
        add(playerPanel, gbc);

        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(10, 10, 10, 10);

        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        buttonPanel.add(leaveButton, gbcButton);

        gbcButton.gridx = 1;
        buttonPanel.add(startGameButton, gbcButton);

        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }

    public void showPlayers(List<String> players) {

        playerPanel.removeAll();

        playerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPlayers = new GridBagConstraints();
        gbcPlayers.insets = new Insets(10, 10, 10, 10);

        for (int i = 0; i < players.size(); i++) {
            gbcPlayers.gridx = 0;
            gbcPlayers.gridy = i;
            gbcPlayers.gridwidth = 2;
            gbcPlayers.weightx = 1.0;

            JLabel playerName = new JLabel(players.get(i));
            playerLabels.add(playerName);
            playerPanel.add(playerName, gbcPlayers);
        }
    }

    public void isStartGameButtonVisible(boolean visible) {
        startGameButton.setOpaque(visible);
    }
}
