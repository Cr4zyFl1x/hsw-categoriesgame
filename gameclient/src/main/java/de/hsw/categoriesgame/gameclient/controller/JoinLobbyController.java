package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.JoinLobbyView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for JoinLobbyView to enable doing actions
 */
public class JoinLobbyController {

    private static final Logger log = LoggerFactory.getLogger(JoinLobbyController.class);
    private final ViewManager viewManager;
    private final JoinLobbyView view;

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
        view.getBackButton().addActionListener(e -> goToStartView());
        view.getJoinButton().addActionListener(e -> goToGameRoundView());
    }

    /**
     * Navigates to the start screen
     */
    private void goToStartView() {
        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }

    /**
     * Navigates into the waiting screen
     */
    private void goToGameRoundView() {
        if (validateInputs()) {
            // TODO: 11.10.2024 CategorieGame prüfen, ob lobby verfügbar (Vergleich mit GameConfigs und aktuelle Spieleranzahl)
            log.info("GO TO GAME ROUND VIEW");
            viewManager.changeView(View.WAITING);
        } else {
            view.throwErrorDialog();
        }
    }

    private boolean validateInputs() {
        // TODO: 11.10.2024 CategorieGame prüfen, ob lobby verfügbar (Vergleich mit GameConfigs und aktuelle Spieleranzahl)

        // TODO: Suche nach Lobbycode;
        return true;
    }

}
