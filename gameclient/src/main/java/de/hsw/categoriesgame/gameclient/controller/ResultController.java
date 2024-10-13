package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.ResultView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to control actions on the ResultView
 */
public class ResultController {

    private static final Logger log = LoggerFactory.getLogger(ResultController.class);
    private final ViewManager viewManager;
    private final ResultView view;
    private final GameModel model;


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
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates into a new game round
     */
    private void goToGameRoundView() {
        // reset round count
        model.setCurrentRoundNumber(0);

        // TODO: 11.10.2024 start new round

        log.info("GO TO GAME ROUND VIEW");
        viewManager.changeView(View.GAME_ROUND);
    }

    /**
     * Calculates the top 3 players of the game
     */
    private void calculatePlacements() {
        List<PlayerBean> players = model.getLobby().getActualPlayers();

        players.sort((e1, e2) -> e2.getPoints().compareTo(e1.getPoints()));
        ArrayList<PlayerBean> sortedList = new ArrayList<>(players);

        view.getPlayer1Label().setText(sortedList.get(0).getName());

        if (sortedList.size() > 1) {
            view.getPlayer2Label().setText(sortedList.get(1).getName());
        } else {
            view.getPlayer2Label().setVisible(false);
        }

        if (sortedList.size() > 2) {
            view.getPlayer3Label().setVisible(false);
        } else {
            view.getPlayer3Label().setText(sortedList.get(2).getName());
        }
    }
}
