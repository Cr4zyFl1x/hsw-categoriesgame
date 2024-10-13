package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.api.PlayerResult;
import de.hsw.categoriesgame.gameapi.api.RoundResults;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameapi.api.RoundResults;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.pojos.Pair;
import de.hsw.categoriesgame.gameclient.views.AnswerOverviewView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Controller class which enables logical operations can be made on AnswerOverviewView
 */
public class AnswerOverviewController {

    private static final Logger log = LoggerFactory.getLogger(AnswerOverviewController.class);
    private final ViewManager viewManager;
    private final AnswerOverviewView view;
    private final  GameModel model;

    // TODO: mockPlayers ersetzen durch model.getPlayers()!!!

    List<String> mockPlayers;

    /**
     * Constructor
     * @param viewManager   View Manager to enable navigation between different views
     * @param view          referring view of the controller
     * @param model         model in which game data will be saved
     */
    public AnswerOverviewController(ViewManager viewManager, AnswerOverviewView view, GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.model = model;

        mockPlayers = new ArrayList<>();

        mockPlayers.add("SwaggerBoi33");
        mockPlayers.add("StadtBanause12");
        mockPlayers.add("Andy");

        registerListener();
        createAnswerOverview(
                model.getPlayerBeans().stream().map(PlayerBean::getName).toList(),
                model.getCategories(),
                model.getLobby().getCurrentRoundResults());

        showPoints(mockPlayers);

    }

    /**
     * registers all ActionListeners
     */
    private void registerListener() {
        view.getCancelButton().addActionListener(e -> goToStartView());
        view.getContinueButton().addActionListener(e -> goToResultOrGameRoundView());
    }

    /**
     * Navigates to the start screen
     */
    private void goToStartView() {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates to the result or next game round view
     */
    private void goToResultOrGameRoundView() {
        if (isRoundAmountReached()) {
            log.info("GO TO RESULT VIEW");
            this.updateAnswersInGameModel();
            viewManager.changeView(View.RESULTS);
        } else {
            log.info("GO TO GAME ROUND VIEW");
            this.updateAnswersInGameModel();
            viewManager.changeView(View.GAME_ROUND);
        }
    }

    /**
     * Checks if the selected amount of rounds was reached
     * @return  true - the amount of rounds was reached / false - the amount is not reached yet
     */
    private boolean isRoundAmountReached() {
        return model.getGameConfiguration().getMaxRounds() == model.getCurrentRoundNumber();
    }

    /**
     * Builds the overall answer overview according to the amount of players and categories
     * @param players           amount of players
     * @param categories        selected categories
     */
    private void createAnswerOverview(List<String> players, List<String> categories, RoundResults roundResults) {
        view.createAnswerOverview(players, categories, roundResults);
    }

    /**
     * Builds the point overview according to the amount of players
     * @param players     players
     */
    private void showPoints(List<String> players) {
        view.showPoints(players);
    }

    /**
     * Updating the answers and the status (doubted / not doubted) in the game model
     */
    private void updateAnswersInGameModel() {
        List<Pair<String, Boolean>> answers = new ArrayList<>();

        for (int i = 0; i < view.getCategoryAnswerLabels().size(); i++) {
            for (int j = 0; j < view.getCategoryAnswerLabels().get(i).size(); j++) {

                String answer = view.getCategoryAnswerLabels().get(i).get(j).getText();
                Boolean isDoubted = view.getDoubtAnswerCheckboxes().get(i).get(j).isSelected();

                Pair<String, Boolean> pair = new Pair<>(answer, isDoubted);
                answers.add(pair);
            }
        }
    }
}