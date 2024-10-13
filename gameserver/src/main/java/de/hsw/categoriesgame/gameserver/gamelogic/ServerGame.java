package de.hsw.categoriesgame.gameserver.gamelogic;

import de.hsw.categoriesgame.gameapi.api.*;
import de.hsw.categoriesgame.gameapi.mapper.Mapper;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.*;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Slf4j
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
    private final HashMap<Integer, RoundResults> roundResults = new HashMap<>();


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

    @Getter
    private HashMap<Integer, List<PlayerBean>> calculatedPointsBeans = new HashMap<>();



    public ServerGame(List<Client> clients, GameConfigs gameConfigs)
    {
        if (clients == null || gameConfigs == null) {
            throw new IllegalArgumentException("Params must not be null!");
        }
        this.gameConfigs = gameConfigs;
        this.clients = Collections.synchronizedList(clients);
        this.currentRoundState = GameRoundState.PREPARING;
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

        // Is state ANSWERS_OPENED / ANSWERS_CLOSE
//        if (!(currentRoundState.equals(GameRoundState.ANSWERS_OPEN) || currentRoundState.equals(GameRoundState.ANSWERS_CLOSED))) {
//            throw new IllegalStateException("ILLEGAL STATE - Server is not in state to receive answers. - Server is in state [" + currentRoundState + "]");
//        }

        // Is this player the first?
        if (!existAnswersForCurrentRound()) {
            roundResults.put(currentRoundNumber, new RoundResults(currentRoundNumber));

            // Get Results for Current Round (Empty)
            final RoundResults roundResults = getCurrentRoundResults();

            // Add first result
            roundResults.addResult(playerResult);

            // Send ANSWERS_CLOSED to the clients that they send their answers
            log.info("Player {} has answered as first. Answering time is up!", playerResult.getPlayerBean().getName());
            log.debug("Results {}", roundResults);
            updateRoundState(GameRoundState.ANSWERS_CLOSED);

            return;
        }

        //
        // All other players
        //

        // Has player already answered?
        final RoundResults roundResults = getCurrentRoundResults();
        if (roundResults.hasAnswered(playerResult.getPlayerBean())) {
            log.warn("Player {} has already answered!", playerResult.getPlayerBean());
            return;
        }

        // Otherwise set result
        roundResults.addResult(playerResult);

        log.info("{} Players have answered now!", roundResults.getAmountOfAnswers());

        // If all have answered -> Show answers
        if (haveAllAnswered()) {
            log.info("All players have answered!");

            log.debug("Calculating points per Player for round {}", currentRoundNumber);
            getCurrentRoundResults().calculatePointsForRound();
            updateRoundState(GameRoundState.SHOW_ROUND_ANSWERS);
        }
    }

    public int getPointsForPlayer(final PlayerBean player)
    {
        if (!isStarted()) {
            throw new IllegalStateException("Game has not been started yet!");
        }
        int points = 0;
        for (RoundResults roundResults : roundResults.values()) {

            final Optional<PlayerResult> result = roundResults.getPlayerResults().values().stream()
                    .filter(j -> j.getPlayerBean().equals(player)).findFirst();

            points += result.orElseThrow(() -> new IllegalStateException("Player not found to get points in round " + roundResults.getRound())).getPoints();
        }
        player.setPoints(points);
        return points;
    }

    public List<PlayerBean> getUpdatedPlayerPoints()
    {
        if (getCalculatedPointsBeans().containsKey(getCurrentRoundNumber())) {
            return getCalculatedPointsBeans().get(getCurrentRoundNumber());
        }

        List<PlayerBean> beans = new ArrayList<>();
        for (Client client : getClients()) {
            final PlayerBean bean = Mapper.map(client);
            bean.setPoints(getPointsForPlayer(bean));
            beans.add(bean);
        }

        getCalculatedPointsBeans().put(getCurrentRoundNumber(), beans);

        return beans;
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
    public RoundResults getCurrentRoundResults()
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
     * Client leave
     * @param client
     */
    public void leaveClient(Client client)
    {
        this.clients.remove(client);

        if (getClients().size() == 1) {
            updateRoundState(GameRoundState.PENULTIMATE_PLAYER_LEFT);
        }
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
        final List<Client> clients1 = getClients();
        synchronized (clients1) {
            for (Client client : clients1)
            {
                client.notifyRoundState(newRoundState, new GameData(getCurrentChar(), getCurrentRoundNumber()));
            }
        }
    }

    private synchronized List<Client> getClients()
    {
        return Collections.synchronizedList(new ArrayList<>(clients));
    }
}