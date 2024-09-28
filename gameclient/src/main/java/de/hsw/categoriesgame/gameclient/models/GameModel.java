package de.hsw.categoriesgame.gameclient.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class contains all necessary data while the game is running
 */
public class GameModel {

    // TODO: Punkte für jeden Spieler speichern

    HashMap<String, Integer> points;
    private char currentLetter;
    List<String> categories;
    List<String> players;

    public GameModel() {
        categories = new ArrayList<>();
        players = new ArrayList<>();
        points = new HashMap<>();
    }

    public List<String> getCategories() {
        return categories;
    }

    public int getCategoriesCount() {
        return categories.size();
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public void removeCategory(String category) {
        categories.remove(category);
    }

    public void clearCategories() {
        categories.clear();
    }

    public List<String> getPlayers() {
        return players;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public void addPlayer(String player) {
        players.add(player);
    }

    public void removePlayer(String player) {
        players.remove(player);
    }

    public void clearPlayers() {
        players.clear();
    }

    public char getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(char currentLetter) {
        this.currentLetter = currentLetter;
    }

    public HashMap<String, Integer> getPoints() {
        return points;
    }
}
