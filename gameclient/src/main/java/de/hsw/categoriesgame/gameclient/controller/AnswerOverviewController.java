package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.AnswerOverviewView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class which enables logical operations can be made on AnswerOverviewView
 */
public class AnswerOverviewController {

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
        createAnswerOverview(mockPlayers, model.getCategories());
        showPoints(mockPlayers);

    }

    /**
     * registers all ActionListeners
     */
    private void registerListener() {
        view.getCancelButton().addActionListener(e -> goToLobbiesView());
        view.getContinueButton().addActionListener(e -> goToResultOrGameRoundView());
    }

    /**
     * Navigates to the start screen
     */
    private void goToLobbiesView() {
        System.out.println("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates to the result or next game round view
     */
    private void goToResultOrGameRoundView() {
        if (isRoundAmountReached()) {
            System.out.println("GO TO RESULT VIEW");
            viewManager.changeView(View.RESULTS);
        } else {
            System.out.println("GO TO GAME ROUND VIEW");
            viewManager.changeView(View.GAME_ROUND);
        }
    }

    /**
     * Checks if the selected amount of rounds was reached
     * @return  true - the amount of rounds was reached / false - the amount is not reached yet
     */
    private boolean isRoundAmountReached() {
        return model.getAmountRounds() == model.getCurrentRoundNumber();
    }

    /**
     * Builds the overall answer overview according to the amount of players and categories
     * @param players           amount of players
     * @param categories        selected categories
     */
    private void createAnswerOverview(List<String> players, List<String> categories) {
        // TODO: Antworten sind mit Platzhaltern belegt -> Antworten der Spieler anzeigen!

        view.createAnswerOverview(players, categories);
    }

    /**
     * Builds the point overview according to the amount of players
     * @param players     players
     */
    private void showPoints(List<String> players) {
        view.showPoints(players);
    }

}
