package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.*;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import de.hsw.categoriesgame.gameapi.util.BugfixUtil;
import de.hsw.categoriesgame.gameclient.GameclientApplication;
import de.hsw.categoriesgame.gameclient.interfaces.ExecutorCategory;
import de.hsw.categoriesgame.gameclient.interfaces.RunnableExecutor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel implements RunnableExecutor<ExecutorCategory> {

    /**
     * Logger
     */
    private static final Logger log = LoggerFactory.getLogger(GameModel.class);

    /**
     * The lobby (REMOTE!)
     */
    @Setter @Getter
    private Lobby lobby;

    /**
     * The represented client / player (REMOTE!)
     */
    @Setter @Getter
    private Client localClient;

    /**
     * Players in the game
     */
    @Getter @Setter
    private List<PlayerBean> playerBeans;

    /**
     * The lobby code
     */
    @Getter
    private String lobbyCode;

    /**
     * The categories the players have to answer
     */
    @Getter @Setter
    private List<String> categories = new ArrayList<>();

    /**
     * The configuration of the game
     * <p>
     *     Such as max players ...
     * </p>
     */
    @Getter
    private GameConfigs gameConfiguration;


    //
    //// DYNAMIC VARIABLES FOR ROUNDS
    //


    /**
     * The current game (round) state
     */
    @Getter @Setter
    private GameRoundState gameRoundState;

    /**
     * The current letter
     */
    @Getter @Setter
    private char currentLetter;

    /**
     * The current round number
     */
    @Getter @Setter
    private int currentRoundNumber;

    /**
     * Defines if the player represented by this client has answered
     */
    @Getter @Setter
    private boolean localPlayerAnswered;

    /**
     * The current answers the user has typed in for this round
     */
    @Getter @Setter
    private List<String> temporaryAnswers = BugfixUtil.getListWithEmptyStrings(5);



    /**
     * Constructor
     */
    public GameModel()
    {
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


    //
    //// MANAGING ANSWERS
    //


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


    /**
     * Transforms answer list in such a way that it only contains the words beginning with the current letter.
     *
     * @return the validated list of answers
     */
    private List<String> validatedTemporaryAnswers() {
        var answers = getTemporaryAnswers();

        return answers.stream()
                .map(s -> s.toUpperCase().trim().startsWith(String.valueOf(getCurrentLetter())) ? s : "")
                .toList();
    }



    //
    //// MODEL INITIALIZATION & RESET
    //



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
        this.temporaryAnswers = List.of("", "", "", "", "");
    }


    /**
     * Initializes the Model with required game data
     * <p>
     *     During join of lobby
     * </p>
     */
    public void initialize()
    {
        if (getLobby() == null || getLocalClient() == null) {
            throw new IllegalStateException("A local player and the lobby needs to be set in order to initialize()!");
        }
        this.categories = lobby.getGameConfigs().getCategories();
        this.playerBeans = lobby.getPlayers();
        this.lobbyCode = lobby.getLobbyCode();
        this.gameConfiguration = lobby.getGameConfigs();
        this.gameRoundState = GameRoundState.PREPARING;
        this.temporaryAnswers = BugfixUtil.getListWithEmptyStrings(gameConfiguration.getCategories().size());
    }



    //////////////////////////////////////////////////////
    //////////////////////////////////////////////////////



    /**
     * Map containing the runnables to call when a respective event occurs
     */
    final HashMap<String, Runnable> runnables = new HashMap<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public void register(String category, Runnable runnable)
    {
        runnables.put(category, runnable);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void callRunnable(String category)
    {
        final Runnable runnable = runnables.get(category);

        if (runnable != null) {
            runnable.run();
        }
    }
}