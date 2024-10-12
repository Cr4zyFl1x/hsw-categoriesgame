package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import de.hsw.categoriesgame.gameclient.pojos.Pair;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObservable;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel implements AdvancedObservable<ObservableCategory> {

    /**
     * The lobby (REMOTE use little!)
     */
    @Setter
    @Getter
    private Lobby lobby;

    @Setter
    @Getter
    private Client localClient;

    /**
     * Players in the game
     */
    @Getter
    private List<PlayerBean> playerBeans;


    private char currentLetter;
    private int amountRounds;
    private int currentRoundNumber;
    private final List<String> categories;
    private List<Pair<String, Boolean>> answersDoubted;

    private List<String> answers;

    @Getter
    @Setter
    private RoundState roundState;

    /**
     * Constructor
     */
    public GameModel() {
        categories = new ArrayList<>();
        playerBeans = new ArrayList<>();
    }
    //TODO: Lobby Methode aufrufen (sendAnsw)
    //TODO: "" evaluateAnswers aufrufen

    public void startNewRound() {
        lobby.startNewRound();
        this.currentLetter = lobby.getCurrentLetter();
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

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
    //TODO: Muss mit der maximalen game nummer verglichen werden (vom Server), start von jeder Runde
    /**
     * Returns all categories
     * @return  list of all categories
     */
    public List<String> getCategories() {
        return categories;
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
     * clears the complete category list
     */
    public void clearCategories() {
        categories.clear();
    }

    /**
     * Returns the list including all players
     * @return  list of players
     */
    public List<PlayerBean> getPlayerBeans() {
        return playerBeans;
    }
    //TODO: Bekommt man vom Server -> von Lobby
    /**
     * returns the amount of players
     * @return  amount of players
     */
    public int getPlayerCount() {
        return playerBeans.size();
    }

    /**
     * adds a new player to the player list
     * @param playerBean    new player to be added
     */
    public void addPlayer(PlayerBean playerBean) {
        playerBeans.add(playerBean);
    }

    /**
     * removes a certain player from the player list
     * @param playerBean    player to be removed
     */
    public void removePlayer(PlayerBean playerBean) {
        playerBeans.remove(playerBean);
    }

    /**
     * clears the complete player list
     */
    public void clearPlayers() {
        playerBeans.clear();
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


    public void leave() throws IllegalStateException
    {
        if (localClient == null && lobby != null) {
            throw new IllegalStateException("A lobby is existing but no LocalPlayer. WRONG STATE!");
        }

        // TODO:
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

    public void sendAnswers() {
        var list = new ArrayList<NormalAnswer>();
        for (int i = 0; i < categories.size(); i++) {
            var clientUUID = localClient.getUUID();
            var category = categories.get(i);
            var answer = answers.get(i);
            list.add(new NormalAnswer(clientUUID, category, answer));
        }
        lobby.sendAnswers(list);
    }


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
        observers.get(category).add(observer);
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