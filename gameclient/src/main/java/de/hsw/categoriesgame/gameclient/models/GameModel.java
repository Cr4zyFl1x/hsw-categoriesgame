package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.CategorieGame;
import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.LobbyNotFoundException;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameclient.GameclientApplication;
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
public class GameModel implements AdvancedObservable<ObservableCategory> {

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
    private List<PlayerBean> playerBeans;

    @Getter
    private String lobbyCode;

    @Getter
    private final List<String> categories;

    private char currentLetter;
    private int amountRounds;
    private int currentRoundNumber;

    private List<Pair<String, Boolean>> answersDoubted;

    /**
     * Constructor
     */
    public GameModel() {
        categories = new ArrayList<>();
        playerBeans = new ArrayList<>();
    }


    /**
     * Get initial data from remote to avoid doing this again and again
     */
    public void initialize()
    {
        if (lobby == null || localClient == null)
            throw new IllegalStateException("For initialization the lobby and local client must be present!");

        this.lobbyCode = lobby.getLobbyCode();
        updatePlayers();
    }












    //TODO: Lobby Methode aufrufen (sendAnsw)
    //TODO: "" evaluateAnswers aufrufen
    /**
     * returns the amount of rounds
     * @return      amount of rounds
     */
    public int getAmountRounds() {
        return amountRounds;
    }
    //TODO: Bekommt man vom Server -> von der Lobby
    /**
     * sets the amount of rounds
     * @param amountRounds      amounts of rounds
     */
    public void setAmountRounds(int amountRounds) {
        this.amountRounds = amountRounds;
    }
    //TODO: Muss in die GameConfi rein, an Server geschickt werden
    /**
     * Returns the current round number
     * @return  current round
     */
    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }
    //TODO: Bekommt man vom Server
    /**
     * Sets the current round number
     * @param currentRoundNumber    current round
     */
    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }


    //TODO: Bekommt man vom Server
    //TODO: SetCategories erstellen
    /**
     * Returns the amount of categories
     * @return  amount of categories
     */
    public int getCategoriesCount() {
        return categories.size();
    }

    /**
     * adds a new category to the category list
     * @param category  new category to add
     */
    public void addCategory(String category) {
        categories.add(category);
    }

    //TODO: Bekommt man vom Server
    /**
     * removes a category from the category list
     * @param category  category to be removed
     */
    public void removeCategory(String category) {
        categories.remove(category);
    }


    /**
     * returns the amount of players
     * @return  amount of players
     */
    public int getPlayerCount() {
        return playerBeans.size();
    }


    /**
     * Returns the current active letter
     * @return  active letter
     */
    //TODO: Den aktuellen Letter aus der Lobby holen
    public char getCurrentLetter() {
        return currentLetter;
    }

    /**
     * Sets a new current letter
     * @param currentLetter     new active letter
     */
    //TODO: PAssiert durch start new game
    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }


    public void setAnswersDoubted(List<Pair<String, Boolean>> answersDoubted) {
        this.answersDoubted = answersDoubted;
    }


    public List<Pair<String, Boolean>> getAnswersDoubted() {
        return answersDoubted;
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

        // Notification required in WaitingList
        sendNotification(ObservableCategory.LOBBY_WAIT_CONTROLLER);
    }


    /**
     * Resets the whole model
     */
    public void reset()
    {
        this.currentLetter = 0;
        this.amountRounds = 0;
        this.currentRoundNumber = 0;
        this.categories.clear();
        this.playerBeans.clear();
        this.lobby = null;
        this.localClient = null;
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
}