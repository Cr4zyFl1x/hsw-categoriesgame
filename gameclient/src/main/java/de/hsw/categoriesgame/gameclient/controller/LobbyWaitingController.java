package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.models.ObservableCategory;
import de.hsw.categoriesgame.gameclient.views.LobbyWaitingView;
import de.hsw.categoriesgame.gameclient.views.View;
import de.hsw.categoriesgame.gameclient.views.ViewManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LobbyWaitingController implements AdvancedObserver {

    private static final Logger log = LoggerFactory.getLogger(LobbyWaitingController.class);
    private final ViewManager viewManager;
    private final LobbyWaitingView view;
    private final GameModel gameModel;


    public LobbyWaitingController(final ViewManager viewManager,
                                  final LobbyWaitingView view,
                                  final GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.gameModel = model;

        gameModel.register(ObservableCategory.LOBBY_WAIT_CONTROLLER, this);

        registerListener();
        updateJoinedPlayers();
        isStartGameButtonVisible();
        view.setLobbyCode(gameModel.getLobbyCode());
    }

    private void registerListener()
    {
        view.getLeaveButton().addActionListener(e -> {
            view.getLeaveButton().setEnabled(false);
            new Thread(this::leaveButtonPressed).start();
        });
        view.getStartGameButton().addActionListener(e -> startGameButtonPressed());
    }

    /**
     * Shows the button to start a game depending on if the player is the admin of the lobby
     */
    private void isStartGameButtonVisible()
    {
        final PlayerBean localPlayer = Mapper.map(gameModel.getLocalClient());
        view.getStartGameButton().setVisible(gameModel.getLobby().isAdmin(localPlayer));
    }


    /**
     * Leave lobby
     */
    private void leaveButtonPressed()
    {
        try {
            gameModel.leave();
        } catch (Exception e) {

            // Log error but go back to start
            log.error(e.getMessage(), e);
        }

        log.info("GO TO START VIEW");
        viewManager.changeView(View.START);
    }


    /**
     * Button for the lobby admin to start the game
     */
    private void startGameButtonPressed()
    {
        log.info("GO TO GAME ROUND VIEW");
        gameModel.getLobby().startGame();
        viewManager.changeView(View.GAME_ROUND);
    }


    /**
     * Updates the list in the waiting room with the joined players
     */
    private void updateJoinedPlayers()
    {
        view.showPlayers(gameModel.getPlayerBeans().stream().map(PlayerBean::getName).toList());
    }


    /////////////////////////////////////////////
    /////////////////////////////////////////////


    @Override
    public void receiveNotification()
    {
        log.debug("Lobby has changed! Processing change.");
        updateJoinedPlayers();
        isStartGameButtonVisible();
    }
}