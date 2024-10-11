package de.hsw.categoriesgame.gameclient.models;

import de.hsw.categoriesgame.gameclient.pojos.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel {
    private char currentLetter;
    private int amountRounds;
    private int currentRoundNumber;
    private final List<String> categories;
    private final List<Player> players;

    /**
     * Constructor
     */
    public GameModel() {
        categories = new ArrayList<>();
        players = new ArrayList<>();
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
}
