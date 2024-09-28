package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.JoinLobbyView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

/**
 * Controller class for JoinLobbyView to enable doing actions
 */
public class JoinLobbyController {

    ViewManager viewManager;
    JoinLobbyView view;

    /**
     * Constructor
     * @param viewManager   ViewManager to be able to navigate between views
     * @param view          referring view of the controller
     */
    public JoinLobbyController(ViewManager viewManager, JoinLobbyView view) {
        this.viewManager = viewManager;
        this.view = view;

        registerListener();
    }

    /**
     * Register all ActionListeners
     */
    private void registerListener() {
        view.getBackButton().addActionListener(e -> goToLobbiesView());
        view.getJoinButton().addActionListener(e -> goToGameRoundView());
    }

    /**
     * Navigates to the start screen
     */
    private void goToLobbiesView() {
        System.out.println("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates into a game round
     */
    private void goToGameRoundView() {
        if (validateInputs()) {
            System.out.println("GO TO GAME ROUND VIEW");
            viewManager.changeView(View.GAME_ROUND);
        } else {
            view.throwErrorDialog();
        }
    }

    private boolean validateInputs() {
        // TODO: Suche nach Lobbycode; falls gefunden dann true zurückgeben, ansonsten false
        return false;
    }

}
