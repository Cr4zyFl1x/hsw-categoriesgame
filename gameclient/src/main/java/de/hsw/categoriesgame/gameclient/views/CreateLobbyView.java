package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class in which the view of creating a lobby is being built
 */
public final class CreateLobbyView extends JPanel implements InitializableView {

    private JLabel adminUsernameLabel;

    @Getter
    private JTextField adminUsernameInput;

    private JLabel newLobbyHeader;

    private JLabel configHeader;

    @Getter
    private JTextField lobbyCodeInput;

    private JLabel lobbyCodeLabel;

    @Getter
    private JButton reloadLobbyCodeButton;

    private JLabel maxPlayerLabel;

    @Getter
    private JSpinner maxPlayerSpinner;

    private JLabel amountRoundsLabel;

    @Getter
    private JSpinner amountRoundsSpinner;

    @Getter
    private JButton cancelButton;

    @Getter
    private JButton createButton;

    private JLabel newCategoryLabel;

    @Getter
    private JTextField newCategoryInput;

    @Getter
    private JButton addCategoryButton;

    private JLabel activeCategoriesLabel;

    private JPanel activeCategoriesPanel;

    @Getter
    private List<JButton> categoryButtons;

    private JSeparator separator;

    @Getter
    public JButton categoryButton;



    /**
     * Constructor
     */
    public CreateLobbyView() {
        initializeComponents();
        buildView();
    }



    /**
     * Initializes all components needed
     */
    @Override
    public void initializeComponents() {
        // Header when creating a new lobby
        newLobbyHeader = new JLabel("New Lobby", JLabel.CENTER);
        newLobbyHeader.setFont(new Font("Arial", Font.BOLD, 24));

        // Header above the configurations
        configHeader = new JLabel("Configuration", JLabel.LEFT);
        configHeader.setFont(new Font("Arial", Font.BOLD, 18));

        // Admin username components
        adminUsernameLabel = new JLabel("Admin Username:");
        adminUsernameInput = new JTextField();

        // Lobby code components
        lobbyCodeLabel = new JLabel("Lobby Code:");
        lobbyCodeInput = new JTextField();
        lobbyCodeInput.setEditable(false);
        reloadLobbyCodeButton = new JButton("Regenerate");

        // Max amount of player components
        maxPlayerLabel = new JLabel("Max Player:");
        maxPlayerSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));

        // Amount rounds components
        amountRoundsLabel = new JLabel("Amount Rounds:");
        amountRoundsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));

        // Components for adding a new category
        newCategoryLabel = new JLabel("New Category:");
        newCategoryInput = new JTextField();
        addCategoryButton = new JButton("Add Category");

        // Active Categories components
        activeCategoriesLabel = new JLabel("Active Categories:");
        categoryButton = new JButton();

        // Button to create the lobby
        createButton = new JButton("Create");
        createButton.setBackground(new Color(25, 175, 50));
        createButton.setForeground(Color.WHITE);

        // Button to cancel the creation process
        cancelButton = new JButton("Cancel");

        // Separator component to seperate general options and category options
        separator = new JSeparator(SwingConstants.HORIZONTAL);

        // Liste für aktive Kategorien initialisieren
        categoryButtons = new ArrayList<>();

        // separate panel to keep the active categories
        activeCategoriesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    }


    /**
     * Builds the final view
     */
    @Override
    public void buildView() {
        // Setting up GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 15, 15, 15);

        // Add header (New Lobby)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(newLobbyHeader, gbc);

        // Setting up configPanel
        JPanel configPanel = new JPanel(new GridBagLayout());
        GridBagConstraints configGbc = new GridBagConstraints();
        configGbc.fill = GridBagConstraints.HORIZONTAL;
        configGbc.insets = new Insets(10, 10, 10, 10);

        // Add Header (Configuration)
        configGbc.gridx = 0;
        configGbc.gridy = 0;
        configGbc.gridwidth = 2;
        configPanel.add(configHeader, configGbc);

        // Add Admin Username label and input
        configGbc.gridx = 0;
        configGbc.gridy = 1;
        configGbc.gridwidth = 1;
        configGbc.weightx = 0.2;
        configPanel.add(adminUsernameLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(adminUsernameInput, configGbc);



        // Add Lobby Code label and input
        configGbc.gridx = 0;
        configGbc.gridy = 2;
        configGbc.gridwidth = 1;
        configGbc.weightx = 0.2;
        configPanel.add(lobbyCodeLabel, configGbc);


        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        JPanel codeRLPanel = new JPanel(new GridBagLayout());
        GridBagConstraints codeRLGbc = new GridBagConstraints();
        codeRLGbc.fill = GridBagConstraints.HORIZONTAL;

        codeRLGbc.gridy = 1;
        codeRLGbc.gridx = 0;
        codeRLGbc.gridwidth = 1;
        codeRLGbc.weightx = 0.95;
        codeRLPanel.add(lobbyCodeInput, codeRLGbc);

        codeRLGbc.gridy = 1;
        codeRLGbc.gridx = 1;
        codeRLGbc.weightx = 0.005;
        codeRLPanel.add(reloadLobbyCodeButton, codeRLGbc);

        configPanel.add(codeRLPanel, configGbc);



        // Add Max Player label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 3;
        configGbc.weightx = 0.2;
        configPanel.add(maxPlayerLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(maxPlayerSpinner, configGbc);

        // Add Amount Rounds label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 4;
        configGbc.weightx = 0.2;
        configPanel.add(amountRoundsLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(amountRoundsSpinner, configGbc);

        // Add Amount Rounds label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 5;
        configGbc.gridwidth = 2;
        configGbc.fill = GridBagConstraints.HORIZONTAL;
        configPanel.add(separator, configGbc);

        // Add New Category label, input, and button
        configGbc.gridwidth = 1;
        configGbc.gridy = 6;
        configGbc.gridx = 0;
        configGbc.weightx = 0.2;
        configPanel.add(newCategoryLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(newCategoryInput, configGbc);

        configGbc.gridx = 2;
        configGbc.weightx = 0;
        configPanel.add(addCategoryButton, configGbc);

        // Add Active Categories label
        configGbc.gridx = 0;
        configGbc.gridy = 7;
        configGbc.gridwidth = 2;
        configGbc.weightx = 0.2;
        configPanel.add(activeCategoriesLabel, configGbc);

        // Add panel with active categories to config panel
        configGbc.gridy = 8;
        configGbc.gridwidth = 2;
        configPanel.add(activeCategoriesPanel, configGbc);

        // Add config panel to the main panel
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        add(configPanel, gbc);

        // Setting up button panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(10, 10, 10, 10);

        // Cancel button
        buttonGbc.gridx = 0;
        buttonPanel.add(cancelButton, buttonGbc);

        // Create button
        buttonGbc.gridx = 1;
        buttonPanel.add(createButton, buttonGbc);

        // Add buttons panel at the bottom
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);
    }


    /**
     * Adds a new category to the active ones
     * @param category  category to be added
     */
    public JButton addActiveCategory(String category) {
        // Create a new button for the category
        JButton categoryButton = new JButton(category);
        categoryButton.setPreferredSize(new Dimension(100, 30));

        // Add the button into the panel and the button list
        activeCategoriesPanel.add(categoryButton);
        categoryButtons.add(categoryButton);

        // Revalidate and repaint the panel
        activeCategoriesPanel.revalidate();
        activeCategoriesPanel.repaint();

        // Return the created button for further action listener setup
        return categoryButton;
    }


    /**
     * removes a chosen category from the active ones
     * @param categoryButton    category which should be removed
     */
    public void removeCategory(JButton categoryButton) {
        // Removes the button from the panel and from the list
        activeCategoriesPanel.remove(categoryButton);
        categoryButtons.remove(categoryButton);

        // revalidate and repaint the panel
        activeCategoriesPanel.revalidate();
        activeCategoriesPanel.repaint();
    }

    /**
     * Shows a error message dialogue with a desired message
     * @param msg   the message
     */
    public void throwErrorDialog(final String msg) {
        JOptionPane.showMessageDialog(null, msg, "Es ist ein Fehler aufgetreten", JOptionPane.ERROR_MESSAGE);
    }
}