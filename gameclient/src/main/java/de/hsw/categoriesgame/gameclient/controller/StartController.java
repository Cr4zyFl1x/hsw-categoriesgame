package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.StartView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;

import java.util.logging.Logger;

/**
 * Controller class for StartView to handle logic operations
 */
public class StartController {

    private static final Logger log = Logger.getLogger(StartController.class.getName());
    ViewManager viewManager;
    StartView view;

    /**
     * Constructor
     * @param viewManager   viewManager object to allow navigation between views
     * @param view          referring view of the controller
     */
    public StartController(ViewManager viewManager, StartView view) {
        this.viewManager = viewManager;
        this.view = view;

        registerListener();
    }

    /**
     * Registers all ActionListeners
     */
    private void registerListener() {
        view.getJoinLobbyButton().addActionListener(e -> goToJoinLobbyView());
        view.getCreateLobbyButton().addActionListener(e -> goToCreateLobbyView());
    }

    /**
     * Navigates the user to the join lobby view
     */
    private void goToJoinLobbyView() {
        log.info("GO TO JOIN LOBBY VIEW");
        viewManager.changeView(View.JOIN_LOBBY);
    }

    /**
     * Navigates the user to the lobby creation view
     */
    private void goToCreateLobbyView() {
        log.info("GO TO CREATE LOBBY VIEW");
        viewManager.changeView(View.CREATE_LOBBY);
    }

}
