package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.ViewBuild;

import javax.swing.*;
import java.awt.*;

/**
 * View class which builds the view for joining a lobby
 */
public class JoinLobbyView extends JPanel implements ViewBuild {

    private JLabel joinHeader;
    private JLabel codeLabel;
    private JLabel nameLabel;
    private JTextField codeInput;
    private JTextField nameInput;
    private JButton joinButton;
    private JButton backButton;
    private JPanel buttonPanel;
    private JPanel inputPanel;

    /**
     * Constructor
     */
    public JoinLobbyView() {
        initializeComponents();
        buildView();
    }

    /**
     * Returns the JLabel header
     * @return  JLabel
     */
    public JLabel getJoinHeader() {
        return joinHeader;
    }

    /**
     * Returns the label for the code input row
     * @return  JLabel
     */
    public JLabel getCodeLabel() {
        return codeLabel;
    }

    /**
     * Returns the label for the name input row
     * @return  JLabel
     */
    public JLabel getNameLabel() {
        return nameLabel;
    }

    /**
     * returns the input field for the code input row
     * @return  JTextField
     */
    public JTextField getCodeInput() {
        return codeInput;
    }

    /**
     * returns the input field for the name input row
     * @return  JTextField
     */
    public JTextField getNameInput() {
        return nameInput;
    }

    /**
     * Returns the join button
     * @return  JButton
     */
    public JButton getJoinButton() {
        return joinButton;
    }

    /**
     * Returns the back button
     * @return  JButton
     */
    public JButton getBackButton() {
        return backButton;
    }

    /**
     * Initializes all components needed
     */
    @Override
    public void initializeComponents() {
        // Header
        joinHeader = new JLabel("Join Game", JLabel.CENTER);
        joinHeader.setFont(new Font("Arial", Font.BOLD, 20));

        // Code input row
        codeLabel = new JLabel("Code:");
        codeInput = new JTextField();

        // name input row
        nameLabel = new JLabel("Name:");
        nameInput = new JTextField();

        // Join button
        joinButton = new JButton("Join");
        joinButton.setBackground(new Color(25, 175, 50));

        // Back button
        backButton = new JButton("Back");

        // panels for input and buttons
        inputPanel = new JPanel();
        buttonPanel = new JPanel();
    }

    /**
     * Builds the final view
     */
    @Override
    public void buildView() {
        // Setting up GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(joinHeader, gbc);

        // Setting up layout for inputPanel
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcInputPanel = new GridBagConstraints();
        gbcInputPanel.fill = GridBagConstraints.BOTH;
        gbcInputPanel.insets = new Insets(10, 10, 10, 10);

        // add name label to input panel
        gbcInputPanel.gridx = 0;
        gbcInputPanel.gridy = 0;
        gbcInputPanel.weightx = 0;
        inputPanel.add(nameLabel, gbcInputPanel);

        // add name input field to input panel
        gbcInputPanel.gridx = 1;
        gbcInputPanel.weightx = 1.0;
        inputPanel.add(nameInput, gbcInputPanel);

        // add code label to input panel
        gbcInputPanel.gridx = 0;
        gbcInputPanel.gridy = 1;
        gbcInputPanel.weightx = 0;
        inputPanel.add(codeLabel, gbcInputPanel);

        // add code input field to input panel
        gbcInputPanel.gridx = 1;
        gbcInputPanel.weightx = 1.0;
        inputPanel.add(codeInput, gbcInputPanel);

        // add input panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(inputPanel, gbc);

        // setting up layout for button panel
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.fill = GridBagConstraints.NONE;
        gbcButtons.insets = new Insets(10, 10, 10, 10);

        // add back button to button panel
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        buttonPanel.add(backButton, gbcButtons);

        // add join button to button panel
        gbcButtons.gridx = 1;
        buttonPanel.add(joinButton, gbcButtons);

        // add button panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        add(buttonPanel, gbc);
    }

    public void throwErrorDialog() {
        JOptionPane.showMessageDialog(null, "Es existiert keine Lobby unter diesem Lobbycode",
                "Lobby nicht gefunden", JOptionPane.ERROR_MESSAGE);
    }
}
