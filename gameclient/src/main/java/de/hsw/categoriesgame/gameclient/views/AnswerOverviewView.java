package de.hsw.categoriesgame.gameclient.views;
import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which builds the view for the answer overview
 */
public class AnswerOverviewView extends JPanel implements InitializableView {

    private JLabel header;
    private JButton doubtButton;
    private JButton continueButton;
    private JButton cancelButton;
    private JPanel answerPanel;
    private JPanel pointsPanel;
    private List<JLabel> playerNameLabels;
    private List<List<JLabel>> categoryAnswerLabels;
    private List<JLabel> playerPointsLabels;
    private List<List<JCheckBox>> doubtAnswerCheckboxes;

    /**
     * Constructor
     */
    public AnswerOverviewView() {
        initializeComponents();
        buildView();
    }

    /**
     * Returns the JLabel header
     * @return  JLabel
     */
    public JLabel getHeader() {
        return header;
    }

    /**
     * Returns the doubt button
     * @return  JButton
     */
    public JButton getDoubtButton() {
        return doubtButton;
    }

    /**
     * Returns the continue button
     * @return  JButton
     */
    public JButton getContinueButton() {
        return continueButton;
    }

    /**
     * Returns the cancel Button
     * @return  JButton
     */
    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Returns a list of all player Labels
     * @return  List<JLabel>
     */
    public List<JLabel> getPlayerNameLabels() {
        return playerNameLabels;
    }

    /**
     * Returns a nested list of the answers of players to every category
     * @return  List<List<JLabel>>
     */
    public List<List<JLabel>> getCategoryAnswerLabels() {
        return categoryAnswerLabels;
    }

    /**
     * Returns a list of player labels for the point overview
     * @return  List<JLabel>
     */
    public List<JLabel> getPlayerPointsLabels() {
        return playerPointsLabels;
    }

    public List<List<JCheckBox>> getDoubtAnswerCheckboxes() {
        return doubtAnswerCheckboxes;
    }

    /**
     * Initializing all components needed
     */
    @Override
    public void initializeComponents() {
        // Lists for dynamic overviews
        playerNameLabels = new ArrayList<>();
        categoryAnswerLabels = new ArrayList<>();
        doubtAnswerCheckboxes = new ArrayList<>();
        playerPointsLabels = new ArrayList<>();

        // Header
        header = new JLabel("Answer Overview", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));

        // Continue button
        continueButton = new JButton("Continue");
        continueButton.setBackground(new Color(25, 175, 50));
        continueButton.setForeground(Color.WHITE);

        // cancel button
        cancelButton = new JButton("Cancel");
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
        add(header, gbc);

        // Adding a answerPanel to the main panel
        answerPanel = new JPanel(new GridBagLayout());
        answerPanel.setBorder(BorderFactory.createTitledBorder("Game Information"));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(answerPanel, gbc);

        // Adding a pointsPanel to the main panel
        pointsPanel = new JPanel(new GridBagLayout());
        pointsPanel.setBorder(BorderFactory.createTitledBorder("Points Overview"));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(pointsPanel, gbc);

        // setting up gridbaglayout for a button panel
        JPanel panelButton = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.insets = new Insets(20, 20, 20, 20);
        gbcButton.fill = GridBagConstraints.HORIZONTAL;

        // adding cancel button to button panel
        gbcButton.gridwidth = 1;
        gbcButton.gridy = 3;
        gbcButton.gridx = 1;
        panelButton.add(cancelButton, gbcButton);

        // adding continue button to button panel
        gbcButton.gridx = 2;
        panelButton.add(continueButton, gbcButton);

        // adding button panel to main panel
        gbc.gridy = 4;
        add(panelButton, gbc);
    }

    /**
     * Creates an answer overview based on the amount of players and categories
     * @param players           players
     * @param categories        selected categories
     */
    public void createAnswerOverview(List<String> players, List<String> categories) {
        // clearing the panel and lists
        answerPanel.removeAll();
        playerNameLabels.clear();
        categoryAnswerLabels.clear();

        // Setting up GridBagLayout
        GridBagConstraints gbcPanel = new GridBagConstraints();
        gbcPanel.insets = new Insets(10, 10, 10, 10);
        gbcPanel.fill = GridBagConstraints.HORIZONTAL;
        gbcPanel.weightx = 1.0;

        // Adding category labels in first row
        for (int c = 0; c < categories.size(); c++) {
            gbcPanel.gridx = c * 2 + 1;
            gbcPanel.gridy = 0;
            gbcPanel.gridwidth = 2; // Adjust for label and checkbox
            gbcPanel.weightx = 1.0;
            JLabel categoryLabel = new JLabel(categories.get(c), JLabel.CENTER);
            answerPanel.add(categoryLabel, gbcPanel);
        }

        // Adding players rows including their answers and checkboxes
        for (int p = 0; p < players.size(); p++) {
            List<JLabel> answerLabels = new ArrayList<>();
            List<JCheckBox> checkboxes = new ArrayList<>();

            // Adding player names
            gbcPanel.gridx = 0;
            gbcPanel.gridy = p + 1;
            gbcPanel.gridwidth = 1;
            JLabel playerNameLabel = new JLabel(players.get(p), JLabel.LEFT);
            answerPanel.add(playerNameLabel, gbcPanel);
            playerNameLabels.add(playerNameLabel);

            // Adding answers including checkboxes
            for (int categoryIndex = 0; categoryIndex < categories.size(); categoryIndex++) {
                gbcPanel.gridx = categoryIndex * 2 + 1;
                gbcPanel.gridy = p + 1;
                gbcPanel.weightx = 1.0;
                JLabel answerLabel = new JLabel("Answer " + (categoryIndex + 1), JLabel.CENTER);
                answerPanel.add(answerLabel, gbcPanel);
                answerLabels.add(answerLabel);

                // Adding checkboxes
                gbcPanel.gridx = categoryIndex * 2 + 2;
                gbcPanel.gridy = p + 1;
                gbcPanel.weightx = 0.1;
                JCheckBox doubtCheckbox = new JCheckBox();
                checkboxes.add(doubtCheckbox);
                answerPanel.add(doubtCheckbox, gbcPanel);
            }

            // Adding answer labels to list
            categoryAnswerLabels.add(answerLabels);
            doubtAnswerCheckboxes.add(checkboxes);
        }

        // revalidate and repaint the answer panel
        answerPanel.revalidate();
        answerPanel.repaint();
    }

    /**
     * Creates an overview of the points based on the amount of players
     * @param players     players
     */
    public void showPoints(List<String> players) {
        // check if pointsPanel is not null
        if (pointsPanel != null) {
            remove(pointsPanel);
        }

        // Setting up panel and Gridbaglayout
        pointsPanel = new JPanel(new GridBagLayout());
        pointsPanel.setBorder(BorderFactory.createTitledBorder("Points Overview"));
        GridBagConstraints gbcPoints = new GridBagConstraints();
        gbcPoints.insets = new Insets(10, 10, 10, 10);
        gbcPoints.fill = GridBagConstraints.HORIZONTAL;

        // Adding player and points
        for (int p = 0; p < players.size(); p++) {
            // Players
            gbcPoints.gridx = 0;
            gbcPoints.gridy = p;
            gbcPoints.gridwidth = 1;
            JLabel playerNameLabel = new JLabel(players.get(p), JLabel.LEFT);
            pointsPanel.add(playerNameLabel, gbcPoints);

            // Points
            gbcPoints.gridx = 1;
            JLabel pointsLabel = new JLabel("0", JLabel.LEFT);
            pointsPanel.add(pointsLabel, gbcPoints);
            playerPointsLabels.add(pointsLabel);
        }

        // Add points panel to main panel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 10, 10);
        add(pointsPanel, gbc);

        // revalidate and repaint points panel
        revalidate();
        repaint();
    }
}
