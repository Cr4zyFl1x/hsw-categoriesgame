package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.api.RoundResults;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameclient.interfaces.ExecutorCategory;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.views.AnswerOverviewView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class which enables logical operations can be made on AnswerOverviewView
 */
public class AnswerOverviewController {

    private static final Logger log = LoggerFactory.getLogger(AnswerOverviewController.class);
    private final ViewManager viewManager;
    private final AnswerOverviewView view;
    private final  GameModel model;


    /**
     * Constructor
     * @param viewManager   View Manager to enable na vigation between different views
     * @param view          referring view of the controller
     * @param model         model in which game data will be saved
     */
    public AnswerOverviewController(ViewManager viewManager, AnswerOverviewView view, GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.model = model;


        model.register(ExecutorCategory.ROUND_STATE_CHANGE.name(), this::onGameRoundStateChanged);
        model.register(ExecutorCategory.PLAYER_JOIN_LEAVE.name(), this::onPlayerJoinLeave);

        isContinueButtonVisible();

        registerListener();
        createAnswerOverview(
                model.getPlayerBeans(),
                model.getCategories(),
                model.getLobby().getCurrentRoundResults());

        List<PlayerBean> beans = model.getLobby().getActualPlayers();
        showPoints(beans);

    }

    /**
     * registers all ActionListeners
     */
    private void registerListener() {
        view.getCancelButton().addActionListener(e -> leaveButtonPressed());
        view.getContinueButton().addActionListener(e -> continueButtonPressed());
    }

    /**
     * Navigates to the start screen
     */
    private void leaveButtonPressed() {

        try {
            model.leave();
        } catch (Exception e) {
            // Log error but go back to start
            log.error(e.getMessage(), e);
        }

        log.info("LEAVE LOBBY AND GO TO START VIEW");

        if (!model.getPlayerBeans().isEmpty()) {
            viewManager.changeView(View.START);
        }
    }

    /**
     * Navigates to the result or next game round view
     */
    private void continueButtonPressed()
    {
        model.startRound();
    }

    /**
     * Shows the button to start a game depending on if the player is the admin of the lobby
     */
    private void isContinueButtonVisible()
    {
        final PlayerBean localPlayer = Mapper.map(model.getLocalClient());
        view.getContinueButton().setVisible(model.getLobby().isAdmin(localPlayer));
    }


    /**
     * Builds the overall answer overview according to the amount of players and categories
     * @param players           amount of players
     * @param categories        selected categories
     */
    private void createAnswerOverview(List<PlayerBean> players, List<String> categories, RoundResults roundResults) {
        view.createAnswerOverview(players, categories, roundResults);
    }

    /**
     * Builds the point overview according to the amount of players
     * @param players     players
     */
    private void showPoints(List<PlayerBean> players) {
        view.showPoints(players);
    }



    ////////////////////////////////////
    ////////////////////////////////////

    public void onPlayerJoinLeave()
    {
        isContinueButtonVisible();
    }

    public void onGameRoundStateChanged()
    {
        final GameRoundState state = model.getGameRoundState();

        if (GameRoundState.ANSWERS_OPEN.equals(state))
        {
            SwingUtilities.invokeLater(() -> viewManager.changeView(View.GAME_ROUND));
            return;
        }

        if (GameRoundState.FINAL_RESULTS.equals(state)) {
            SwingUtilities.invokeLater(() -> viewManager.changeView(View.RESULTS));
            return;
        }

        if (GameRoundState.PENULTIMATE_PLAYER_LEFT.equals(state)) {
            SwingUtilities.invokeLater(() -> viewManager.changeView(View.RESULTS));
            return;
        }
    }
}