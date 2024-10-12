package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.LobbyWaitingView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class LobbyWaitingController {

    private static final Logger log = LoggerFactory.getLogger(LobbyWaitingController.class);
    private final ViewManager viewManager;
    private final LobbyWaitingView view;
    private final List<String> mockPlayer;

    public LobbyWaitingController(ViewManager viewManager, LobbyWaitingView view) {
        this.viewManager = viewManager;
        this.view = view;

        mockPlayer = new ArrayList<>();

        mockPlayer.add("Steven");
        mockPlayer.add("Hans");
        mockPlayer.add("Peter");

        registerListener();
        showActivePlayers(mockPlayer);
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

    // TODO: 11.10.2024 alle bisherigen spieler in einem textfeld anzeigen lassen (getPlayers aus Lobby) 

    private void goToStartView() {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    private void goToGameRoundView() {
        log.info("GO TO GAME ROUND VIEW");
        viewManager.changeView(View.GAME_ROUND);
    }

    private void showActivePlayers(List<String> players) {
        // TODO: mockPlayer durch echte Spieler austauschen!!
        view.showPlayers(players);
    }

}
