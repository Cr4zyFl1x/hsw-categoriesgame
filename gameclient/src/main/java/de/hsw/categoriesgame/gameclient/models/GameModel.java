package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.*;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import de.hsw.categoriesgame.gameclient.GameclientApplication;
import de.hsw.categoriesgame.gameclient.interfaces.ExecutorCategory;
import de.hsw.categoriesgame.gameclient.interfaces.RunnableExecutor;
import de.hsw.categoriesgame.gameclient.pojos.Pair;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObservable;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel implements RunnableExecutor<ExecutorCategory> {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(GameModel.class);

    /**
     * The lobby (REMOTE use little!)
     */
    @Setter @Getter
    private Lobby lobby;

    /**
     * The represented client / player (REMOTE use little)
     */
    @Setter @Getter
    private Client localClient;

    /**
     * Players in the game
     */
    @Getter
    @Setter
    private List<PlayerBean> playerBeans;


    @Getter
    private String lobbyCode;


    @Getter
    @Setter
    private List<String> categories = new ArrayList<>();



    @Getter
    private GameConfigs gameConfiguration;


    //
    //// RUNDENABHÄNGIGE VARIABLEN
    //


    @Getter
    @Setter
    private GameRoundState gameRoundState;

    @Getter
    @Setter
    private char currentLetter;

    @Getter
    @Setter
    private int currentRoundNumber;

    @Getter
    @Setter
    private boolean localPlayerAnswered;

    @Getter
    @Setter
    private List<String> temporaryAnswers = new ArrayList<>();

    @Getter
    @Setter
    private boolean gameStarted;



    /**
     * Constructor
     */
    public GameModel() {
        playerBeans = new ArrayList<>();
    }


    //
    //// ROUND / LOBBY CONTROL
    //



    /**
     * If the user/client is currently in a lobby/game it leaves from it.
     *
     * @throws IllegalStateException if user is not existing or not in lobby etc.
     */
    public void leave() throws IllegalStateException
    {
        if (localClient == null && lobby != null) {
            throw new IllegalStateException("A lobby is existing but no LocalPlayer. WRONG STATE!");
        }

        try {
            CategorieGame game = GameclientApplication.getRemoteGame();
            game.leaveLobby(getLobby(), getLocalClient());
        } catch (UserNotInLobbyException | LobbyNotFoundException e) {
            log.error("Unable to leave user/client from lobby!", e);
            throw new IllegalStateException("Unable to leave user/client from lobby!", e);
        }

        // Reset model to be ready for new game
        this.reset();
    }


    /**
     * Starts the game
     */
    public void startGame()
    {
        lobby.startGame();
    }


    /**
     * Starts a new round
     */
    public void startRound()
    {
        lobby.startRound();
        localPlayerAnswered = false;
    }


    /**
     * Sends the locally temporary stored answers to the server
     */
    public void sendMyAnswer()
    {
        if (localPlayerAnswered) {
            log.warn("This client has already answered!");
            return;
        }

        // Build answer POJO
        final PlayerBean localPlayer = Mapper.map(getLocalClient());
        final PlayerResult roundResult = new PlayerResult(localPlayer, validatedTemporaryAnswers());

        // Send Answer to server
        lobby.receivePlayerAnswer(roundResult);

        localPlayerAnswered = true;

        log.info("My Answer was sent!");
    }


    private List<String> validatedTemporaryAnswers() {
        var answers = getTemporaryAnswers();

        return answers.stream()
                .map(s -> s.toUpperCase().startsWith(String.valueOf(currentLetter)) ? s : "")
                .toList();
    }


    /**
     * Resets the whole model
     */
    public void reset()
    {
        this.currentLetter = 0;
        this.currentRoundNumber = 0;
        this.categories = new ArrayList<>();
        this.playerBeans.clear();
        this.lobby = null;
        this.localClient = null;
        this.runnables.clear();
        this.gameRoundState = GameRoundState.PREPARING;
        this.localPlayerAnswered = false;
        this.temporaryAnswers = new ArrayList<>();
        this.gameStarted = false;
    }



    public void initialize()
    {
        this.categories = lobby.getGameConfigs().getCategories();
        this.playerBeans = lobby.getPlayers();
        this.lobbyCode = lobby.getLobbyCode();
        this.gameConfiguration = lobby.getGameConfigs();
    }

  
    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////


    final HashMap<String, Runnable> runnables = new HashMap<>();

    @Override
    public void register(String category, Runnable runnable)
    {
            runnables.put(category, runnable);
    }

    @Override
    public void callRunnable(String category) {

        final Runnable runnable = runnables.get(category);

        if (runnable != null) {
            runnable.run();
        }
    }
}