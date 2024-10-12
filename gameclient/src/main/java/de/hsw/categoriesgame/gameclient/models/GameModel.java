package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameapi.api.Lobby;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameapi.pojo.RoundState;
import de.hsw.categoriesgame.gameclient.pojos.Pair;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObservable;
import de.hsw.categoriesgame.gameclient.interfaces.AdvancedObserver;
import de.hsw.categoriesgame.gameclient.pojos.Player;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel implements AdvancedObservable<ObservableCategory> {

    @Setter
    @Getter
    private Lobby lobby;

    @Setter
    @Getter
    private de.hsw.categoriesgame.gameapi.api.Player localPlayer;

    private char currentLetter;

    private int amountRounds;

    private int currentRoundNumber;
  
    private final List<String> categories;
    private final List<Player> players;
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
        players = new ArrayList<>();
        answers = new ArrayList<>();
    }

    // tmp
    public void startNewGame() {
        lobby.startGame(new GameConfigs(3, 5));
    }

    public void startNewRound() {
        lobby.startNewRound();
        this.currentLetter = lobby.getCurrentLetter();
    }

    public void sendAnswers() {
        List<NormalAnswer> normalAnswers = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            var category = categories.get(i);
            var answer = answers.get(i);
            normalAnswers.add(new NormalAnswer(localPlayer.getUUID(), category, answer));
        }
        lobby.sendAnswers(normalAnswers);
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
    public List<Player> getPlayers() {
        return players;
    }
    //TODO: Bekommt man vom Server -> von Lobby
    /**
     * returns the amount of players
     * @return  amount of players
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * adds a new player to the player list
     * @param player    new player to be added
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * removes a certain player from the player list
     * @param player    player to be removed
     */
    public void removePlayer(Player player) {
        players.remove(player);
    }

    /**
     * clears the complete player list
     */
    public void clearPlayers() {
        players.clear();
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    /**
     * Returns the current active letter
     *
     * @return active letter
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


    public void reset()
    {
        this.currentLetter = 0;
        this.amountRounds = 0;
        this.currentRoundNumber = 0;
        this.categories.clear();
        this.players.clear();
        this.lobby = null;
        this.localPlayer = null;
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

        for (ObservableCategory cat : category) {
            observers.get(cat).forEach(AdvancedObserver::receiveNotification);
        }
    }
}