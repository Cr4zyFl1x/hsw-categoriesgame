package de.hsw.categoriesgame.gameclient;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.GameData;
import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameclient.interfaces.ExecutorCategory;
import de.hsw.categoriesgame.gameclient.models.GameModel;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
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
     * Called when the round state changes
     * @param roundState    the new round state
     * @param gameData      the game data
     */
    @Override
    public void notifyRoundState(GameRoundState roundState, GameData gameData)
    {
        log.debug("notifyRoundState was called with {}", roundState);
        this.currentGame.setGameRoundState(roundState);
        this.currentGame.setCurrentRoundNumber(gameData.getCurrentRound());
        this.currentGame.setCurrentLetter(gameData.getCurrentLetter());

        // Notify Runnables to handle round state change
        this.currentGame.callRunnable(ExecutorCategory.ROUND_STATE_CHANGE);
    }


    /**
     * Called when player joins/leaves
     * @param players   Actual list of players
     */
    @Override
    public void notifyPlayerJoinLeave(List<PlayerBean> players)
    {
        log.debug("notifyPlayerJoinLeave was called with {} players", players.size());
        currentGame.setPlayerBeans(players);

        this.currentGame.callRunnable(ExecutorCategory.PLAYER_JOIN_LEAVE);
    }
}