package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
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
public class GameModel implements RunnableExecutor<ExecutorCategory>, AdvancedObservable<ObservableCategory> {

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
    private char currentLetter;

    @Getter
    @Setter
    private int currentRoundNumber;

    @Getter
    @Setter
    private List<String> categories = new ArrayList<>();

    @Getter
    @Setter
    private GameRoundState gameRoundState;

    @Getter
    private GameConfigs gameConfiguration;

    /**
     * Constructor
     */
    public GameModel() {
        playerBeans = new ArrayList<>();
    }




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
     * Reads the userlist from remote and notifies views/controller
     */
    public void updatePlayers()
    {
        this.playerBeans = lobby.getPlayers();


    }


    /**
     * Resets the whole model
     */
    public void reset()
    {
        this.currentLetter = 0;
        this.currentRoundNumber = 0;
        this.categories.clear();
        this.playerBeans.clear();
        this.lobby = null;
        this.localClient = null;
        this.runnables.clear();
        this.observers.clear();
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


    final HashMap<ObservableCategory, List<AdvancedObserver>> observers = new HashMap<>();

    @Override
    public void register(ObservableCategory category, AdvancedObserver observer)
    {
        if (!observers.containsKey(category))
            observers.put(category, new ArrayList<>());

        final List<AdvancedObserver> catObservers = observers.get(category);
        Optional<AdvancedObserver> existingOfType = catObservers.stream()
                        .filter(j -> j.getClass().equals(observer.getClass()))
                        .findFirst();

        existingOfType.ifPresent(catObservers::remove);
        catObservers.add(observer);

//        observers.get(category).add(observer);
    }


    @Override
    public void sendNotification(ObservableCategory... category)
    {
        if (category == null || category.length == 0) {
            for (List<AdvancedObserver> observers : observers.values()) {
                observers.forEach(AdvancedObserver::receiveNotification);
            }
            return;
        }

        for (ObservableCategory cat : observers.keySet()) {
            observers.get(cat).forEach(AdvancedObserver::receiveNotification);
        }
    }

    //////////////////////////////////////////
    //////////////////////////////////////////

    final HashMap<ExecutorCategory, List<Runnable>> runnables = new HashMap<>();

    @Override
    public void register(ExecutorCategory category, Runnable runnable)
    {
        if (!runnables.containsKey(category))
            runnables.put(category, new ArrayList<>());

        runnables.get(category).add(runnable);
    }

    @Override
    public void callRunnable(ExecutorCategory... category) {
        if (category == null || category.length == 0) {
            for (List<Runnable> rbls : runnables.values()) {
                rbls.forEach(Runnable::run);
            }
            return;
        }

        for (ExecutorCategory cat : runnables.keySet()) {
            runnables.get(cat).forEach(Runnable::run);
        }
    }
}