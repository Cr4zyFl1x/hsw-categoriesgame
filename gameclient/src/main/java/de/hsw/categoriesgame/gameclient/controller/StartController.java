package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameclient.views.StartView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller class for StartView to handle logic operations
 */
public final class StartController {

    private static final Logger log = LoggerFactory.getLogger(StartController.class);

    private final ViewManager viewManager;
    private final StartView view;

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
        view.getJoinLobbyButton().addActionListener(e -> joinLobbyButtonPressed());
        view.getCreateLobbyButton().addActionListener(e -> createLobbyButtonPressed());
    }

    /**
     * Navigates the user to the join lobby view
     */
    private void joinLobbyButtonPressed()
    {
        log.debug("User clicked join lobby button");
        viewManager.changeView(View.JOIN_LOBBY);
    }

    /**
     * Navigates the user to the lobby creation view
     */
    private void createLobbyButtonPressed()
    {
        log.debug("User clicked create lobby button");
        viewManager.changeView(View.CREATE_LOBBY);
    }

}
