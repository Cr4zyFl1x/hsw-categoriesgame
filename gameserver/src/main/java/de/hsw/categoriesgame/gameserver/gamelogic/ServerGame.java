package de.hsw.categoriesgame.gameserver.gamelogic;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.GameData;
import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import lombok.Getter;

import java.security.SecureRandom;
import java.util.Hashtable;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class ServerGame {

    private SecureRandom random = new SecureRandom();

    @Getter
    private final GameConfigs gameConfigs;

    /**
     * Clients in this game
     */
    private final List<Client> clients;


    /**
     * The results per round
     */
    private final Hashtable<Integer, RoundResults> roundResults = new Hashtable<>();


    // ÄNDERBARE RUNDEN ZUSTÄNDE

    /**
     * Defines if game has started
     */
    @Getter
    private boolean started = false;

    /**
     * The current character
     */
    @Getter
    private char currentChar;

    /**
     * The current round number
     */
    @Getter
    private int currentRoundNumber = 0;

    /**
     * The current round state
     */
    private GameRoundState currentRoundState;



    public ServerGame(List<Client> clients, GameConfigs gameConfigs)
    {
        if (clients == null || gameConfigs == null) {
            throw new IllegalArgumentException("Params must not be null!");
        }
        this.gameConfigs = gameConfigs;
        this.clients = clients;
    }


    /**
     * Starts game
     */
    public void startGame() {
        if (started)
            throw new IllegalStateException("Game has already been started!");

        started = true;

        startRound();
    }


    /**
     * Starts a new round
     * Is called when:
     * <p>
     *     <ul>User clicks start game in waiting lobby (via .startGame() method)</ul>
     *     <ul>User clicks next round on doubting page</ul>
     * </p>
     */
    public void startRound()
    {
        if (!isStarted())
            throw new IllegalStateException("Game has not been started yet!");

        // If was last round
        if (getGameConfigs().getMaxRounds() == currentRoundNumber) {
            updateRoundState(GameRoundState.FINAL_RESULTS);
            return;
        }

        // Otherwise new round data
        currentRoundNumber++;
        currentChar = (char) random.nextInt(65, 91);

        // Users can send their answers
        updateRoundState(GameRoundState.ANSWERS_OPEN);
    }



    /**
     * Receive Answers for this round!
     * @param playerResult The result
     */
    public void receivePlayerAnswer(final PlayerResult playerResult) {

        // Is this player the first?
        if (!existAnswersForCurrentRound()) {
            roundResults.put(currentRoundNumber, new RoundResults(currentRoundNumber));

            // Get Results for Current Round (Empty)
            final RoundResults roundResults = getCurrentRoundResults();

            // Add first result
            roundResults.addResult(playerResult);

            updateRoundState(GameRoundState.ANSWERS_CLOSED);

            return;
        }

        //
        // All other players
        //

        // Has player already answered?
        final RoundResults roundResults = getCurrentRoundResults();
        if (roundResults.hasAnswered(playerResult.getPlayerBean())) {
            return;
        }

        // Otherwise set result
        roundResults.addResult(playerResult);


        // If all have answered
        if (haveAllAnswered()) {
            updateRoundState(GameRoundState.DOUBTING_OPEN);
        }
    }


    /**
     * Returns true if all players have answered this round
     *
     * @return  true, if all players have answered
     */
    private boolean haveAllAnswered()
    {
        return clients.size() == getCurrentRoundResults().getAmountOfAnswers();
    }


    /**
     * Gets the current rounds results
     *
     * @return The results for the current round
     */
    private RoundResults getCurrentRoundResults()
    {
        if (!existAnswersForCurrentRound())
            throw new IllegalStateException("There are no results for current round " + currentRoundNumber + "!");

        return roundResults.get(currentRoundNumber);
    }


    /**
     * Checks if results exist for this round
     * @return  true if results exist
     */
    private boolean existAnswersForCurrentRound()
    {
        return roundResults.containsKey(currentRoundNumber);
    }


    /**
     * Updates the RoundState
     *
     * @param newRoundState
     */
    private void updateRoundState(final GameRoundState newRoundState)
    {
        // Is same as current?
        if (currentRoundState == newRoundState)
            throw new IllegalStateException("Round state is the same as current round state!");

        // Set state
        this.currentRoundState = newRoundState;

        // Notify clients about new state
        for (Client client : clients)
        {
            client.notifyRoundState(newRoundState, new GameData(getCurrentChar(), getCurrentRoundNumber()));
        }
    }
}