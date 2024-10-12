package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameclient.models.ObservableCategory;
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
public class ResultController implements AdvancedObserver {

    private static final Logger log = LoggerFactory.getLogger(ResultController.class);
    private final ViewManager viewManager;
    private final ResultView view;
    private final GameModel model;

    // TODO: mockPlayers-Referenzen durch getter in model ersetzen

    List<PlayerBean> mockPlayerBeans;

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

        model.register(ObservableCategory.RESULT_CONTROLLER, this);

        mockPlayerBeans = new ArrayList<>();
        mockPlayerBeans.add(new PlayerBean("Jeff"));
        mockPlayerBeans.add(new PlayerBean("Kevin"));
        mockPlayerBeans.add(new PlayerBean("Marc"));

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
        // TODO: 11.10.2024 get current top three players from server

        mockPlayerBeans.sort((e1, e2) -> e2.getPoints().compareTo(e1.getPoints()));
        ArrayList<PlayerBean> sortedList = new ArrayList<>(mockPlayerBeans);

        view.getPlayer1Label().setText(sortedList.get(0).getName());
        view.getPlayer2Label().setText(sortedList.get(1).getName());
        view.getPlayer3Label().setText(sortedList.get(2).getName());
    }

    @Override
    public void receiveNotification()
    {
        System.out.println("I GOT NOTIFIED!");
    }
}
