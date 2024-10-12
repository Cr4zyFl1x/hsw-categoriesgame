package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.models.ObservableCategory;
import de.hsw.categoriesgame.gameclient.views.GameRoundView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

/**
 * Controller class of the GameRoundView to handle logical operations
 */
public class GameRoundController implements AdvancedObserver {

    private static final Logger log = LoggerFactory.getLogger(GameRoundController.class);
    private final ViewManager viewManager;
    private final GameRoundView view;
    private final GameModel model;

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

        model.register(ObservableCategory.GAME_ROUND_CONTROLLER, this);

        // register listeners
        registerListener();

        // prepare view for gameplay
        updateRoundNumber();
        generateCategoryRows(model.getCategories());
        view.getCurrentLetter().setText(String.valueOf(model.getCurrentLetter()));
        registerKeyListeners();
    }


    /**
     * registers all ActionListeners
     */
    private void registerListener()
    {
        view.getFinishButton().addActionListener(e -> finishButtonPressed());
        view.getLeaveRoundButton().addActionListener(e -> leaveButtonPressed());
    }


    private void registerKeyListeners()
    {
        view.getCategoryInputFields().forEach(inputField -> inputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                var answers = view.getCategoryInputFields().stream().map(JTextField::getText).toList();
                model.setAnswers(answers);
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        }));
    }


    /**
     * Updates the game round number
     */
    private void updateRoundNumber()
    {
        // update header in view
        view.getHeader().setText("Game Round #" + model.getCurrentRoundNumber());
    }

    /**
     * Navigate to answer overview
     */
    private void finishButtonPressed()
    {
        // TODO: 11.10.2024 create new NormalAnswer und sendAnswer und evaluate

        if (validateInputs()) {
            log.info("GO TO ANSWER OVERVIEW VIEW");
            var answers = view.getCategoryInputFields().stream().map(JTextField::getText).toList();
            model.setAnswers(answers);
        } else {
            view.throwErrorDialog();
        }
    }

    /**
     * Method checks if all conditions are met to navigate to another view
     * @return  true - conditions are met / false - conditions are not met
     */
    private boolean validateInputs()
    {
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
    private void leaveButtonPressed()
    {
        // TODO: Leave model.leave
        viewManager.changeView(View.START);
    }

    /**
     * Updating the amount of rows on the view according to the amount of categories
     * @param categories  selected categories
     */
    private void generateCategoryRows(List<String> categories)
    {
        view.buildCategoryInputs(categories);
    }

    @Override
    public void receiveNotification() {
        log.debug("Changed! - " + model.getRoundState());

        if (model.getRoundState() == RoundState.DOUBTING_OPEN) {
            viewManager.changeView(View.ANSWERS);
        }
    }
}
