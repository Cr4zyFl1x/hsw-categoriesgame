package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.exception.UserNotInLobbyException;
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
     * @param client    object of class Player
     */
    void joinClient(Client client);

    /**
     * Removes player from lobby.
     * @param client    object of class Player
     */
    void leaveClient(Client client) throws UserNotInLobbyException;

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
    void startGame(GameConfigs gameConfigs);

    /**
     * Start a new round, setup data structures and generate new random letter.
     * @return  random letter for the new round
     */
    void startNewRound();
    void closeAnswerRound();
    void closeDoubtingRound();


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

    List<Entry> getDoubtedAnswers();


    /**
     * Gets the current points of a player
     * @param player
     * @return
     */
    int getPointsOfPlayer(PlayerBean player);


    /**
     * Checks if the given player is an administrator
     *
     * @param playerBean    the player dto
     * @return              true if is administrator
     */
    boolean isAdmin(PlayerBean playerBean);


    /**
     * Gets the administrator player
     *
     * @return  the administrative player
     */
    PlayerBean getAdmin();


    /**
     * Gets a list of players of this lobby
     * @return List of players
     */
    List<PlayerBean> getPlayers();


    /**
     * Returns a list of remote Clients ("Players")
     * @return List of clients in this lobby
     */
    List<Client> getClients();


    /**
     * Checks if game has started
     *
     * @return true if game has started
     */
    boolean hasGameStarted();
}