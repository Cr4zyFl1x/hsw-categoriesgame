package de.hsw.categoriesgame.gameclient.controller;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.LobbyFullException;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameclient.ClientImpl;
import de.hsw.categoriesgame.gameclient.models.GameModel;
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
    private final GameModel gameModel;



    /**
     * Constructor
     * @param viewManager   ViewManager to be able to navigate between views
     * @param view          referring view of the controller
     */
    public JoinLobbyController(final ViewManager viewManager,
                               final JoinLobbyView view,
                               final GameModel model) {
        this.viewManager = viewManager;
        this.view = view;
        this.gameModel = model;

        gameModel.reset();

        registerListener();
    }


    /**
     * Register all ActionListeners
     */
    private void registerListener() {
        view.getBackButton().addActionListener(e -> backToStartButtonPressed());
        view.getJoinButton().addActionListener(e -> {
            view.getJoinButton().setEnabled(false);
            new Thread(this::joinButtonPressed).start();
        });
    }


    /**
     * Navigates to the start screen
     */
    private void backToStartButtonPressed()
    {
        log.debug("User clicked button to go back to start");
        viewManager.changeView(View.START);
    }


    /**
     * Navigates into the waiting screen
     */
    private void joinButtonPressed()
    {
        if (!validateInputs()) {
            view.throwErrorDialog("Bitte f\u00FCllen Sie die Felder \"Name\" und \"Code\" aus.");
            return;
        }

        final Client client = new ClientImpl(gameModel, view.getNameInput().getText());
        final String code = view.getCodeInput().getText();

        try {
            final String lobbyCode = view.getCodeInput().getText().trim();

            final CategorieGame remoteGame = viewManager.getProxyFactory().createProxy(CategorieGame.class);
            final Lobby l = remoteGame.getLobby(lobbyCode);

            if (l.hasGameStarted()) {
                view.throwErrorDialog("Der Lobby konnte nicht beigetreten werden!\nDas Spiel l\u00E4uft bereits.");
                return;
            }

            gameModel.setLobby(l);
            remoteGame.joinLobby(code, client);
            gameModel.setLocalClient(client);

            // Initialize GameModel
            gameModel.initialize();

        } catch (LobbyNotFoundException e) {
            log.error("No Lobby found under Code {}!", code);
            view.throwErrorDialog("Es existiert keine Lobby mit dem Code " + code + "!");
            gameModel.reset();
            return;
        } catch (LobbyFullException e) {
            log.warn("Cannot join lobby, because lobby is full!");
            view.throwErrorDialog("Diese Lobby hat leider schon die maximale Spielerzahl erreicht!");
            gameModel.reset();
            return;
        } catch (Exception e) {
            log.error("Error while joining a Lobby!", e);
            view.throwErrorDialog("Es ist ein Fehler aufgetreten!\nBitte schauen Sie im Protokoll f\u00FCr weitere Informationen.");
            gameModel.reset();
            return;
        } finally {
            view.getJoinButton().setEnabled(true);
        }

        viewManager.changeView(View.WAITING);
    }


    private boolean validateInputs()
    {
        final String name = view.getNameInput().getText();
        final String code = view.getCodeInput().getText();

        return !code.isEmpty() && !name.isEmpty();
    }
}