package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.GameRoundView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import javax.swing.*;
import java.util.List;
import java.util.Random;

/**
 * Controller class of the GameRoundView to handle logical operations
 */
public class GameRoundController {

    ViewManager viewManager;
    GameRoundView view;
    GameModel model;

    /**
     * Constructor
     * @param viewManager   View Manager to make navigation between views possible
     * @param view          referring view of the controller
     * @param model         model in which data will be saved
     */
    public GameRoundController(ViewManager viewManager, GameRoundView view, GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.model = model;

        // register listeners
        registerListener();

        // prepare view for gameplay
        updateRoundNumber();
        generateRandomLetter();
        generateCategoryRows(model.getCategories());
    }

    /**
     * registers all ActionListeners
     */
    private void registerListener() {
        view.getFinishButton().addActionListener(e -> goToAnswerOverviewView());
        view.getLeaveRoundButton().addActionListener(e -> goToStartView());
    }

    /**
     * Updates the game round number
     */
    private void updateRoundNumber() {
        // save changes in model
        int currentRound = model.getCurrentRoundNumber();
        model.setCurrentRoundNumber(currentRound + 1);

        // update header in view
        view.getHeader().setText("Game Round #" + currentRound);
    }

    /**
     * Method generates a random number
     */
    private void generateRandomLetter() {
        // Generate letter
        Random random = new Random();
        char c = (char) (random.nextInt(26) + 'A');

        // Save letter in model
        model.setCurrentLetter(c);

        // Show generated letter in view
        view.getCurrentLetter().setText(String.valueOf(c));
    }

    /**
     * Navigate to answer overview
     */
    private void goToAnswerOverviewView() {
        if (validateInputs()) {
            System.out.println("GO TO ANSWERS OVERVIEW VIEW");
            viewManager.changeView(View.ANSWERS);
        } else {
            view.throwErrorDialog();
        }
    }

    /**
     * Method checks if all conditions are met to navigate to another view
     * @return  true - conditions are met / false - conditions are not met
     */
    private boolean validateInputs() {
        List<JTextField> fields = view.getCategoryInputFields();
        char currentLetter = model.getCurrentLetter();

        for (JTextField field : fields) {
            String input = field.getText().trim();

            // Check if the input field is empty and if the input is unequal to the current letter of the round
            if (input.isEmpty() || input.charAt(0) != currentLetter) {
                return false;
            }
        }
        return true;
    }

    /**
     * Navigate to start screen
     */
    private void goToStartView() {
        System.out.println("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Updating the amount of rows on the view according to the amount of categories
     * @param categories  selected categories
     */
    private void generateCategoryRows(List<String> categories) {
        view.buildCategoryInputs(categories);
    }
}
