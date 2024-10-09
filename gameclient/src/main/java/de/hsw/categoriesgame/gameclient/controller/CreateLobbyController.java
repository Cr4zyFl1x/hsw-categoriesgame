package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.CreateLobbyView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for CreateLobbyView to make operations possible
 */
public class CreateLobbyController {

    private static final Logger log = LoggerFactory.getLogger(CreateLobbyController.class);
    private final ViewManager viewManager;
    private final CreateLobbyView view;
    private final GameModel gameModel;

    // TODO: mockPlayers ersetzen durch model.getPlayers()!!!

    List<String> mockPlayers;

    /**
     * Constructor
     * @param viewManager   View Manager in order to navigate between different views
     * @param view          referring view of the controller
     * @param gameModel     model to save specific data
     */
    public CreateLobbyController(ViewManager viewManager, CreateLobbyView view, GameModel gameModel) {
        this.viewManager = viewManager;
        this.view = view;
        this.gameModel = gameModel;

        mockPlayers = new ArrayList<>();

        mockPlayers.add("1");
        mockPlayers.add("2");
        mockPlayers.add("3");

        clearModel();
        registerListener();
    }

    /**
     * registers all ActionListeners
     */
    private void registerListener() {
        view.getCancelButton().addActionListener(e -> goToStartView());
        view.getCreateButton().addActionListener(e -> goToGameRoundView());
        view.getAddCategoryButton().addActionListener(e -> addNewCategory());
        view.getNewCategoryInput().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addNewCategory();
                }
            }
        });
        view.getCategoryButton().addActionListener(e -> removeCategory(view.getCategoryButton()));
    }

    /**
     * Navigates to the start screen
     */
    private void goToStartView() {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates into the game round view including saving the amount of rounds in the model
     */
    private void goToGameRoundView() {
        if (validateInputs()) {
            // save the amount of rounds in model
            gameModel.setAmountRounds((int) view.getAmountRoundsSpinner().getValue());

            // save the amount of rounds in model
            gameModel.setDoubtsNeeded((int) view.getDoubtsNeededSpinner().getValue());

            // switch view
            log.info("GO TO GAME ROUND VIEW");
            viewManager.changeView(View.GAME_ROUND);
        } else {
            view.throwErrorDialog();
        }
    }

    /**
     * Validates if all input is given correctly, so the player can continue
     * @return  true - conditions are met / false - conditions are not met
     */
    private boolean validateInputs() {
        int maxPlayers = (int) view.getMaxPlayerSpinner().getValue();
        JTextField lobbyCode = view.getLobbyCodeInput();
        int amountCategories = gameModel.getCategoriesCount();
        int amountsNeededCounts = (int) view.getDoubtsNeededSpinner().getValue();

        return maxPlayers >= mockPlayers.size() && !lobbyCode.getText().isEmpty() && amountCategories >= 1 && amountsNeededCounts <= maxPlayers;
    }

    /**
     * Method triggers the addition of a new category including adding it to the model
     */
    private void addNewCategory() {
        JTextField inputField = view.getNewCategoryInput();
        String newCategory = inputField.getText().trim();
        JButton categoryButton = new JButton();

        for (int i = 0; i < view.getCategoryButtons().size(); i++) {
            if (view.getCategoryButtons().get(i).getText().equals(newCategory)) {
                categoryButton = view.getCategoryButtons().get(i);
            }
        }

        if (!newCategory.isEmpty()) {
            // Adding a new button component and getting the reference
            categoryButton = view.addActiveCategory(newCategory);

            // Adding action listener to the newly created category button
            JButton finalCategoryButton = categoryButton;
            categoryButton.addActionListener(e -> removeCategory(finalCategoryButton));

            // Adding category to model
            gameModel.addCategory(newCategory);

            // Clear input
            inputField.setText("");
        }
    }

    /**
     * Removes a certain category
     */
    private void removeCategory(JButton categoryButton) {
        // Removing the button component on the view
        view.removeCategory(categoryButton);

        // Removing the category in the model
        gameModel.removeCategory(categoryButton.getText());
    }

    /**
     * Clears the model when creating a new lobby
     */
    private void clearModel() {
        gameModel.clearCategories();
    }

}
