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
    private int doubtsNeeded;
    private final List<String> categories;
    private final List<Player> players;

    /**
     * Constructor
     */
    public GameModel() {
        categories = new ArrayList<>();
        players = new ArrayList<>();
    }

    /**
     * returns the amount of rounds
     * @return      amount of rounds
     */
    public int getAmountRounds() {
        return amountRounds;
    }

    /**
     * sets the amount of rounds
     * @param amountRounds      amounts of rounds
     */
    public void setAmountRounds(int amountRounds) {
        this.amountRounds = amountRounds;
    }

    /**
     * Returns the current round number
     * @return  current round
     */
    public int getCurrentRoundNumber() {
        return currentRoundNumber;
    }

    /**
     * Sets the current round number
     * @param currentRoundNumber    current round
     */
    public void setCurrentRoundNumber(int currentRoundNumber) {
        this.currentRoundNumber = currentRoundNumber;
    }

    /**
     * Returns all categories
     * @return  list of all categories
     */
    public List<String> getCategories() {
        return categories;
    }

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
    public char getCurrentLetter() {
        return currentLetter;
    }

    /**
     * Sets a new current letter
     * @param currentLetter     new active letter
     */
    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }

    /**
     * returns the amount of doubts needed
     * @return  doubts needed
     */
    public int getDoubtsNeeded() {
        return doubtsNeeded;
    }

    /**
     * sets the amount of doubts needed
     * @param doubtsNeeded  amount of doubts needed
     */
    public void setDoubtsNeeded(int doubtsNeeded) {
        this.doubtsNeeded = doubtsNeeded;
    }
}
