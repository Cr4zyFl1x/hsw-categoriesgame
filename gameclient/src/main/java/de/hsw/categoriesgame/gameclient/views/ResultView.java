package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

/**
 * View class to build the view for the result overview
 */
public final class ResultView extends JPanel implements InitializableView {

    private final Image backgroundImage;

    private JLabel resultHeader;

    @Getter
    private JLabel firstPlaceLabel;

    @Getter
    private JLabel secondPlaceLabel;

    @Getter
    private JLabel thirdPlaceLabel;

    @Getter
    private JLabel player1Label;

    @Getter
    private JLabel player2Label;

    @Getter
    private JLabel player3Label;

    @Getter
    private JButton leaveButton;

    @Getter
    private JButton startAgainButton;

    private JPanel placementPanel;

    private JPanel buttonPanel;



    /**
     * Constructor
     */
    public ResultView() {
        backgroundImage = new ImageIcon("gameclient/src/main/resources/background_2.jpg").getImage();
        setPreferredSize(new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null)));

        initializeComponents();
        buildView();
    }



    /**
     * Initializes all components needed
     */
    @Override
    public void initializeComponents() {
        // Header
        resultHeader = new JLabel("Result", JLabel.CENTER);
        resultHeader.setFont(new Font("Arial", Font.BOLD, 24));

        // First place row
        firstPlaceLabel = new JLabel("First Place:");
        firstPlaceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player1Label = new JLabel("Player 1");
        player1Label.setFont(new Font("Arial", Font.PLAIN, 16));

        // Second place row
        secondPlaceLabel = new JLabel("Second Place:");
        secondPlaceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player2Label = new JLabel("Player 2");
        player2Label.setFont(new Font("Arial", Font.PLAIN, 16));

        // Thrid place row
        thirdPlaceLabel = new JLabel("Third Place:");
        thirdPlaceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        player3Label = new JLabel("Player 3");
        player3Label.setFont(new Font("Arial", Font.PLAIN, 16));

        // leave button
        leaveButton = new JButton("Leave");

        // Start again button
        startAgainButton = new JButton("Start Again");
        startAgainButton.setBackground(new Color(25, 175, 50));
        startAgainButton.setForeground(Color.WHITE);

        // Panels
        placementPanel = new JPanel();
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
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        add(resultHeader, gbc);

        // Setting up layout for placement panel
        placementPanel.setOpaque(false);
        placementPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcPlacement = new GridBagConstraints();
        gbcPlacement.insets = new Insets(10, 10, 10, 10);
        gbcPlacement.fill = GridBagConstraints.HORIZONTAL;

        // add first placement row to placement panel
        gbcPlacement.gridx = 0;
        gbcPlacement.gridy = 0;
        placementPanel.add(firstPlaceLabel, gbcPlacement);

        gbcPlacement.gridx = 1;
        placementPanel.add(player1Label, gbcPlacement);

        // add second placement row to placement panel
        gbcPlacement.gridx = 0;
        gbcPlacement.gridy = 1;
        placementPanel.add(secondPlaceLabel, gbcPlacement);

        gbcPlacement.gridx = 1;
        placementPanel.add(player2Label, gbcPlacement);

        // add third placement row to placement panel
        gbcPlacement.gridx = 0;
        gbcPlacement.gridy = 2;
        placementPanel.add(thirdPlaceLabel, gbcPlacement);

        gbcPlacement.gridx = 1;
        placementPanel.add(player3Label, gbcPlacement);

        // Add placement panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(placementPanel, gbc);

        // setting up layout for button panel
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(10, 10, 10, 10);

        // add leave button to button panel
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        buttonPanel.add(leaveButton, gbcButton);

        // add start again button to button panel
        gbcButton.gridx = 1;
        buttonPanel.add(startAgainButton, gbcButton);

        // add button panel to main panel
        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }

    /**
     * Adds a background image to the panel
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
