package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import de.hsw.categoriesgame.gameclient.models.ObservableCategory;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class ClientImpl implements Client {

    private final GameModel currentGame;

    /**
     * Client UUID (Same as respective {@link de.hsw.categoriesgame.gameapi.pojo.PlayerBean} UUID)
     */
    private final UUID uuid;

    /**
     * Name of the client
     */
    private final String name;

    /**
     * Points
     */
    private int points;

    // Kann das weg?
    @Deprecated
    private boolean hasAnswered;



    public ClientImpl(final GameModel currentGame, final String name) {
        uuid = UUID.randomUUID();
        this.name = name;
        this.currentGame = currentGame;
    }



    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUUID() {
        return uuid;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int getPoints() {
        return points;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setPoints(int points) {
        this.points = points;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasAnswered() {
        return hasAnswered;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void setHasAnswered(boolean hasAnswered) {
        this.hasAnswered = hasAnswered;
    }


    @Override
    public void notifyPlayerAboutRoundState(RoundState roundState) {
        System.out.println("Neuer State: " + roundState.name());
        currentGame.setRoundState(roundState);
        //currentGame.sendNotification(ObservableCategory.ANSWER_CONTROLLER);
        currentGame.sendNotification(ObservableCategory.GAME_ROUND_CONTROLLER);
    }


    @Override
    public void notifyPlayerAboutLobbyState()
    {
        log.debug("Player got notified");
        currentGame.sendNotification(ObservableCategory.LOBBY_WAIT_CONTROLLER);
    }
}