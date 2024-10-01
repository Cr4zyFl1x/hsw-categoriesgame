package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.LobbyWaitingView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import java.util.logging.Logger;

public class LobbyWaitingController {

    private static final Logger log = Logger.getLogger(LobbyWaitingController.class.getName());
    private final ViewManager viewManager;
    private final LobbyWaitingView view;

    public LobbyWaitingController(ViewManager viewManager, LobbyWaitingView view) {
        this.viewManager = viewManager;
        this.view = view;

        registerListener();
    }

    private void registerListener() {
        view.getLeaveButton().addActionListener(e -> goToStartView());
        view.getStartGameButton().addActionListener(e -> goToGameRoundView());
    }

    /**
     * Shows the button to start a game depending on if the player is the admin of the lobby
     */
    private void isStartGameButtonVisible() {
        // TODO: Wenn der Spieler der Admin ist, dann "Start Game" Button anzeigen lassen, ansonsten nicht
        // Methode zum Anzeigen: view.isStartGameButtonVisible(true);
    }

    private void goToStartView() {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    private void goToGameRoundView() {
        log.info("GO TO GAME ROUND VIEW");
        viewManager.changeView(View.GAME_ROUND);
    }

}
