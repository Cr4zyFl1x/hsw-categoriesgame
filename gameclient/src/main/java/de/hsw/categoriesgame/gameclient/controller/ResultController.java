package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.ResultView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller class to control actions on the ResultView
 */
public class ResultController {

    ViewManager viewManager;
    ResultView view;
    GameModel model;

    // TODO: HashMap "points" ersetzen durch model.getPoints()!!!

    HashMap<String, Integer> points;

    /**
     * Constructor
     * @param viewManager   ViewManager to be able to navigate between views
     * @param view          referring view of the controller
     * @param model         model to save and call data
     */
    public ResultController(ViewManager viewManager, ResultView view, GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.model = model;

        points = new HashMap<>();
        points.put("Rick", 10);
        points.put("Astley", 0);
        points.put(":D", 100);

        registerListener();
        calculatePlacements();
    }

    /**
     * Register all ActionListeners
     */
    private void registerListener() {
        view.getLeaveButton().addActionListener(e -> goToStartView());
        view.getStartAgainButton().addActionListener(e -> goToGameRoundView());
    }

    /**
     * Navigates to start screen
     */
    private void goToStartView() {
        System.out.println("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates into a new game round
     */
    private void goToGameRoundView() {
        System.out.println("GO TO GAME ROUND VIEW");
        viewManager.changeView(View.GAME_ROUND);
    }

    /**
     * Calculates the top 3 players of the game
     */
    private void calculatePlacements() {
        List<Map.Entry<String, Integer>> pointsList = new ArrayList<>(points.entrySet());
        pointsList.sort((eintrag1, eintrag2) -> eintrag2.getValue().compareTo(eintrag1.getValue()));
        ArrayList<Map.Entry<String, Integer>> sortierteListe = new ArrayList<>(pointsList);

        view.getPlayer1Label().setText(sortierteListe.get(0).getKey());
        view.getPlayer2Label().setText(sortierteListe.get(1).getKey());
        view.getPlayer3Label().setText(sortierteListe.get(2).getKey());
    }
}
