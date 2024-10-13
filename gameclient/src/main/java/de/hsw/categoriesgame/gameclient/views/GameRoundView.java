package de.hsw.categoriesgame.gameclient.views;
import de.hsw.categoriesgame.gameclient.interfaces.InitializableView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class in which the view for a game round is being built
 */
public class GameRoundView extends JPanel implements InitializableView {

    private JLabel header;
    private JLabel categoryLabel;
    private JTextField categoryInputfield;
    private JLabel currentLetter;
    private JButton finishButton;
    private JButton leaveRoundButton;
    private JPanel categoriesPanel;
    private List<JTextField> categoryInputFields;

    /**
     * Constructor
     */
    public GameRoundView() {
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
     * Returns the label for the certain category
     * @return  JLabel
     */
    public JLabel getCategoryLabel() {
        return categoryLabel;
    }

    /**
     * Returns the input field for a certain category
     * @return  JTextfield
     */
    public JTextField getCategoryInputfield() {
        return categoryInputfield;
    }

    /**
     * returns the button to finish the round
     * @return  JButton
     */
    public JButton getFinishButton() {
        return finishButton;
    }

    /**
     * Returns the button to leave a round
     * @return  JButton
     */
    public JButton getLeaveRoundButton() {
        return leaveRoundButton;
    }

    /**
     * Returns the JLabel for the current Letter
     * @return  JLabel
     */
    public JLabel getCurrentLetter() {
        return currentLetter;
    }

    /**
     * Returns the list of all input fields
     * @return  List<JTextField>
     */
    public List<JTextField> getCategoryInputFields() {
        return categoryInputFields;
    }

    /**
     * Returns the panel on which the category rows are being kept
     * @return  JPanel
     */
    public JPanel getCategoriesPanel() {
        return categoriesPanel;
    }

    /**
     * Initializes all components needed
     */
    @Override
    public void initializeComponents() {
        // Initializing input field list
        categoryInputFields = new ArrayList<>();

        // Header
        header = new JLabel("Game Round #1", JLabel.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));

        // Label for the category
        categoryLabel = new JLabel("Category: ");
        categoryInputfield = new JTextField(20);

        // Finish button
        finishButton = new JButton("Finish");
        finishButton.setForeground(Color.WHITE);
        finishButton.setBackground(new Color(25, 175, 50));
        finishButton.setOpaque(true);
        finishButton.setBorderPainted(false);

        // Leave round button
        leaveRoundButton = new JButton("Leave Round");

        // Active letter label
        currentLetter = new JLabel("A", JLabel.CENTER);
        currentLetter.setFont(new Font("Arial", Font.BOLD, 60));
    }

    /**
     * Builds the final view
     */
    @Override
    public void buildView() {
        // Setting up GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.BOTH;

        // Header
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(header, gbc);

        // Active letter
        gbc.gridy = 1;
        add(currentLetter, gbc);

        // panel for the categories
        categoriesPanel = new JPanel(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(categoriesPanel, gbc);

        // button panel
        JPanel buttonpanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcButtons = new GridBagConstraints();
        gbcButtons.insets = new Insets(10, 10, 10, 10);

        // adding finish button to button panel
        gbcButtons.gridx = 0;
        gbcButtons.gridy = 0;
        gbcButtons.anchor = GridBagConstraints.CENTER;
        buttonpanel.add(finishButton, gbcButtons);

        // adding leave round button to button panel
        gbcButtons.weightx = 0.5;
        gbcButtons.gridy = 3;
        gbcButtons.anchor = GridBagConstraints.WEST;
        buttonpanel.add(leaveRoundButton, gbcButtons);

        // Adding button panel to main panel
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buttonpanel, gbc);
    }

    /**
     * Adds rows for the single categories to fill in the answer according to the amount of categories
     * @param categories  selected categories
     */
    public void buildCategoryInputs(List<String> categories) {
        // Clear panel and list of input fields
        categoriesPanel.removeAll();
        categoryInputFields.clear();

        // Setting up GridBagLayout
        GridBagConstraints gbcCategories = new GridBagConstraints();
        gbcCategories.insets = new Insets(10, 10, 10, 10);

        // Adding components in form of a loop
        for (int i = 0; i < categories.size(); i++) {

            JLabel categoryLabel = new JLabel(categories.get(i));
            JTextField categoryInputField = new JTextField(20);

            // adding label
            gbcCategories.gridx = 0;
            gbcCategories.gridy = i;
            gbcCategories.anchor = GridBagConstraints.WEST;
            categoriesPanel.add(categoryLabel, gbcCategories);

            // adding input field
            gbcCategories.gridx = 1;
            gbcCategories.fill = GridBagConstraints.HORIZONTAL;
            gbcCategories.weightx = 1.0;
            categoriesPanel.add(categoryInputField, gbcCategories);

            // safe input field in list
            categoryInputFields.add(categoryInputField);
        }

        // revalidate and repaint the panel
        categoriesPanel.revalidate();
        categoriesPanel.repaint();
    }

    public void throwErrorDialog() {
        JOptionPane.showMessageDialog(null, """
                        \u00DCberpr\u00FCfe bitte folgende Punkte:\s
                        - Sind alle Felder ausgef\u00FCllt?\s
                        - Beginnen alle deine Eingaben mit dem aktiven Buchstaben?""",
                "Runde kann nicht beendet werden!", JOptionPane.ERROR_MESSAGE);
    }
}
