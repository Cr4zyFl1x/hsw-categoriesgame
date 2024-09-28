package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.pojos.Player;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.ResultView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to control actions on the ResultView
 */
public class ResultController {

    ViewManager viewManager;
    ResultView view;
    GameModel model;

    // TODO: mockPlayers-Referenzen durch getter in model ersetzen

    List<Player> mockPlayers;

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

        mockPlayers = new ArrayList<>();
        mockPlayers.add(new Player("Jeff", 100));
        mockPlayers.add(new Player("Kevin", 80));
        mockPlayers.add(new Player("Marc", 120));

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
        // reset round count
        model.setCurrentRoundNumber(0);

        System.out.println("GO TO GAME ROUND VIEW");
        viewManager.changeView(View.GAME_ROUND);
    }

    /**
     * Calculates the top 3 players of the game
     */
    private void calculatePlacements() {
        mockPlayers.sort((e1, e2) -> e2.getPoints().compareTo(e1.getPoints()));
        ArrayList<Player> sortedList = new ArrayList<>(mockPlayers);

        view.getPlayer1Label().setText(sortedList.get(0).getName());
        view.getPlayer2Label().setText(sortedList.get(1).getName());
        view.getPlayer3Label().setText(sortedList.get(2).getName());
    }
}
