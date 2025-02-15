package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 *  Class in which the view of the start screen is being built
 */
public final class StartView extends JPanel implements InitializableView {

    private final Image backgroundImage;

    @Getter
    private JButton createLobbyButton;

    @Getter
    private JButton joinLobbyButton;

    @Getter
    private JLabel headerLabel;



    /**
     * Constructor
     */
    public StartView() {
        // Setting up background image
        backgroundImage = new ImageIcon("gameclient/src/main/resources/heft.jpg").getImage();
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));

        // Final build of the view
        initializeComponents();
        buildView();
    }



    /**
     * Initializes all components needed
     */
    @Override
    public void initializeComponents() {
        // Header
        headerLabel = new JLabel("Stadt, Land, Fluss", JLabel.CENTER);
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 24));

        // Button to create a lobby
        createLobbyButton = new JButton("Create Lobby");
        createLobbyButton.setPreferredSize(new Dimension(150, 40));

        // button to join a lobby
        joinLobbyButton = new JButton("Join Lobby");
        joinLobbyButton.setPreferredSize(new Dimension(150, 40));
    }


    /**
     * Builds the final view
     */
    @Override
    public void buildView() {
        // Setting up GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);  // More padding for better spacing

        // Add header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(headerLabel, gbc);

        // Add button to create a lobby
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);
        add(createLobbyButton, gbc);

        // Add button to join a lobby
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(joinLobbyButton, gbc);
    }


    /**
     * Adds a background image to the view
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
