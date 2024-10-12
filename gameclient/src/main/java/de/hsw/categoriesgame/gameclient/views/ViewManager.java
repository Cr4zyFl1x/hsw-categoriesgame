package de.hsw.categoriesgame.gameclient.views;

import de.hsw.categoriesgame.gameapi.rpc.ProxyFactory;
import de.hsw.categoriesgame.gameclient.controller.*;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import lombok.Getter;

import javax.swing.*;

public class ViewManager {

    private final JFrame frame;

    @Getter
    private final ProxyFactory proxyFactory;
    private final GameModel gameModel;
    private JPanel currentPanel;

    /**
     * Constructor for the ViewManager
     * @param frame     frame
     */
    public ViewManager(final JFrame frame, final ProxyFactory proxyFactory) {
        this.frame = frame;
        this.proxyFactory = proxyFactory;
        this.gameModel = new GameModel();
    }

    /**
     * Exchanges the current view with another view
     * @param view   new view
     */
    public void changeView(View view) {
        if (currentPanel != null) {
            frame.remove(currentPanel);
        }

        JPanel newView = new JPanel();

        switch (view) {
            case START -> {
                newView = new StartView();
                new StartController(this, (StartView) newView);
            }
            case WAITING -> {
                newView = new LobbyWaitingView();
                new LobbyWaitingController(this, (LobbyWaitingView) newView);
            }
            case JOIN_LOBBY -> {
                newView = new JoinLobbyView();
                new JoinLobbyController(this, (JoinLobbyView) newView, gameModel);
            }
            case CREATE_LOBBY -> {
                newView = new CreateLobbyView();
                new CreateLobbyController(this, (CreateLobbyView) newView, gameModel);
            }
            case GAME_ROUND -> {
                newView = new GameRoundView();
                new GameRoundController(this, (GameRoundView) newView, gameModel);
            }
            case ANSWERS -> {
                newView = new AnswerOverviewView();
                new AnswerOverviewController(this, (AnswerOverviewView) newView, gameModel);
            }
            case RESULTS -> {
                newView = new ResultView();
                new ResultController(this, (ResultView) newView, gameModel);
            }
        }

        frame.add(newView);
        frame.revalidate();
        frame.repaint();

        currentPanel = newView;
    }
}
