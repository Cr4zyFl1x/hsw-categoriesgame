package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.pojo.*;

import java.util.List;

public interface Lobby {

    /**
     * Get unique lobby code that identifies the lobby.
     * @return  lobby code
     */
    String getLobbyCode();

    /**
     * Adds player to lobby.
     * @param player    object of class Player
     */
    void addPlayer(Player player);

    /**
     * Removes player from lobby.
     * @param player    object of class Player
     */
    void removePlayer(Player player);

    /**
     * Get all players in lobby.
     * @return  list of players
     */
    List<Player> getPlayers();

    /**
     * Get player that is currently admin of lobby.
     * @return  object of class Player
     */
    Player getAdmin();

    void setGameConfigs(GameConfigs gameConfigs);
    GameConfigs getGameConfigs();

    /**
     * Set the categories for the game/lobby.
     * @param categories    list of categories (e.g. name, country, ...)
     */
    void setCategories(List<String> categories);

    /**
     * Get the categories of the game/lobby.
     * @return  list of categories as strings
     */
    List<String> getCategories();

    int getCurrentRoundNumber();
    char getCurrentLetter();

    /**
     * Create new object of class Game with players in the lobby.
     */
    void startGame();

    /**
     * Start a new round, setup data structures and generate new random letter.
     * @return  random letter for the new round
     */
    char startNewRound();

    /**
     * Sends list of answers to server to save.
     * @param answers   list of normal answers
     */
    void sendAnswers(List<NormalAnswer> answers);

    /**
     * Player can doubt an answer of another player.
     * @param doubtedAnswer object of DoubtedAnswer
     * @return              list of entries
     */
    List<Entry> doubtAnswer(DoubtedAnswer doubtedAnswer);

    /**
     * Evaluates the answers if all players have answered and gives points for them.
     * @return  list of entries
     */
    List<Entry> evaluateAnswers();

    int getPointsOfPlayer(Player player);

    /**
     * Changes the admin of the lobby if the current admin leaves.
     */
    void changeAdmin();
}
