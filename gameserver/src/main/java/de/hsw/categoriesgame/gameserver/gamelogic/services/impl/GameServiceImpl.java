package de.hsw.categoriesgame.gameserver.gamelogic.services.impl;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.services.GameService;

import java.util.List;

public class GameServiceImpl implements GameService {

    private List<String> categories;
    private final Lobby lobby;

    private char currentLetter;

    public GameServiceImpl(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public boolean addCategory(String category) {
        return false;
    }

    @Override
    public boolean addCategories(List<String> categories) {
        return false;
    }

    @Override
    public boolean removeCategory(String category) {
        return false;
    }

    @Override
    public boolean removeCategories(List<String> categories) {
        return false;
    }

    @Override
    public List<String> getAllCategories() {
        return List.of();
    }

    @Override
    public void evaluateAnswers(char currentLetter, List<List<String>> answersPerCategory) {

    }

    @Override
    public List<List<Integer>> getCurrentPoints() {
        return List.of();
    }

    @Override
    public List<List<Integer>> getCurrentTopThreePlayersWithPoints() {
        return List.of(List.of());
    }

    @Override
    public void setAmountRounds(int amount) {

    }

    @Override
    public int getAmountRounds() {
        return 0;
    }

    @Override
    public void updateRoundNumber() {

    }

    @Override
    public char getCurrentLetter() {
        return 0;
    }

    @Override
    public char generateRandomLetter() {
        return 0;
    }

    public void setCurrentLetter(char c) {
        this.currentLetter = c;
    }

    public Lobby getLobby() {
        return lobby;
    }
}
