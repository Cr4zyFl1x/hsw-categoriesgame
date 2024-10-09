package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class in which the view of creating a lobby is being built
 */
public class CreateLobbyView extends JPanel implements InitializableView {

    private JLabel newLobbyHeader;
    private JLabel configHeader;
    private JTextField lobbyCodeInput;
    private JLabel lobbyCodeLabel;
    private JLabel maxPlayerLabel;
    private JSpinner maxPlayerSpinner;
    private JLabel amountRoundsLabel;
    private JSpinner amountRoundsSpinner;
    private JLabel doubtsNeededLabel;
    private JSpinner doubtsNeededSpinner;
    private JButton cancelButton;
    private JButton createButton;
    private JLabel newCategoryLabel;
    private JTextField newCategoryInput;
    private JButton addCategoryButton;
    private JLabel activeCategoriesLabel;
    private JPanel activeCategoriesPanel;
    private List<JButton> categoryButtons;
    private JSeparator separator;
    public JButton categoryButton;

    /**
     * Constructor
     */
    public CreateLobbyView() {
        initializeComponents();
        buildView();
    }

    /**
     * Returns the JLabel header named "New Lobby"
     * @return  JLabel header
     */
    public JLabel getNewLobbyHeader() {
        return newLobbyHeader;
    }

    /**
     * Returns the JLabel header named "Configurations"
     * @return  JLabel header
     */
    public JLabel getConfigHeader() {
        return configHeader;
    }

    /**
     * Returns the input field for the lobby code
     * @return  JTextfield
     */
    public JTextField getLobbyCodeInput() {
        return lobbyCodeInput;
    }

    /**
     * Returns the JLabel for the lobby code
     * @return  JLabel
     */
    public JLabel getLobbyCodeLabel() {
        return lobbyCodeLabel;
    }

    /**
     * Returns the JLabel for the max amount of Players Spinner
     * @return  JLabel
     */
    public JLabel getMaxPlayerLabel() {
        return maxPlayerLabel;
    }

    /**
     * Returns the cancel button
     * @return  JButton
     */
    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Returns the creation button
     * @return  JButton
     */
    public JButton getCreateButton() {
        return createButton;
    }

    /**
     * Returns the spinner to set the max amount of players
     * @return  JSpinner
     */
    public JSpinner getMaxPlayerSpinner() {
        return maxPlayerSpinner;
    }

    /**
     * Returns the spinner to set the amount of rounds
     * @return  JSpinner
     */
    public JSpinner getAmountRoundsSpinner() {
        return amountRoundsSpinner;
    }

    /**
     * Returns the button to add a category
     * @return  JButton
     */
    public JButton getAddCategoryButton() {
        return addCategoryButton;
    }

    /**
     * Returns the input field to type in a new category
     * @return  JTextfield
     */
    public JTextField getNewCategoryInput() {
        return newCategoryInput;
    }

    /**
     * Returns the panel where all active categories will be collected
     * @return  JPanel
     */
    public JPanel getActiveCategoriesPanel() {
        return activeCategoriesPanel;
    }

    /**
     * Returns the button of a category
     * @return  JButton
     */
    public JButton getCategoryButton() {
        return categoryButton;
    }

    /**
     * Returns the label for the active categories
     * @return  JLabel
     */
    public JLabel getActiveCategoriesLabel() {
        return activeCategoriesLabel;
    }

    /**
     * Returns the label for the input of a new category
     * @return  JLabel
     */
    public JLabel getCategoryLabel() {
        return newCategoryLabel;
    }

    /**
     * Returns the category buttons in form of a list
     * @return  List<JButton>
     */
    public List<JButton> getCategoryButtons() {
        return categoryButtons;
    }

    /**
     * Returns the label for doubts needed spinner component
     * @return
     */
    public JLabel getDoubtsNeededLabel() {
        return doubtsNeededLabel;
    }

    /**
     * Returns the doubts needed Spinner component
     * @return  JSpinner
     */
    public JSpinner getDoubtsNeededSpinner() {
        return doubtsNeededSpinner;
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

        // Lobby code components
        lobbyCodeLabel = new JLabel("Lobby Code:");
        lobbyCodeInput = new JTextField();

        // Max amount of player components
        maxPlayerLabel = new JLabel("Max Player:");
        maxPlayerSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));

        // Amount rounds components
        amountRoundsLabel = new JLabel("Amount Rounds:");
        amountRoundsSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));

        // Amount doubts components
        doubtsNeededLabel = new JLabel("Amount Doubts:");
        doubtsNeededSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

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

        // Add Lobby Code label and input
        configGbc.gridy = 1;
        configGbc.gridwidth = 1;
        configGbc.weightx = 0.2;
        configPanel.add(lobbyCodeLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(lobbyCodeInput, configGbc);

        // Add Max Player label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 2;
        configGbc.weightx = 0.2;
        configPanel.add(maxPlayerLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(maxPlayerSpinner, configGbc);

        // Add Amount Rounds label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 3;
        configGbc.weightx = 0.2;
        configPanel.add(amountRoundsLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(amountRoundsSpinner, configGbc);

        // Add Amount Rounds label and spinner
        configGbc.gridx = 0;
        configGbc.gridy = 4;
        configGbc.weightx = 0.2;
        configPanel.add(doubtsNeededLabel, configGbc);

        configGbc.gridx = 1;
        configGbc.weightx = 1.0;
        configPanel.add(doubtsNeededSpinner, configGbc);

        // Add separator
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

    public void throwErrorDialog() {
        JOptionPane.showMessageDialog(null, """
                Überprüfe bitte auf folgende Punkte:\s
                - Passt deine maximale Spieleranzahl mit der Anzahl teilnehmender Spieler?\s
                - Hast du mindestens eine Kategorie angegeben?\s
                - Hast du einen Lobbycode angegeben?
                - Ist deine Anzahl an benötigten Anzweiflern kleiner oder gleich der Spieleranzahl?""",
                "Lobby kann nicht erstellt werden", JOptionPane.ERROR_MESSAGE);
    }
}
