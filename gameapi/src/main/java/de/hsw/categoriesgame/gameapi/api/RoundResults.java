package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Florian J. Kleine-Vorholt
 */
@Getter
public final class RoundResults implements Serializable {

    /**
     * The round of the answers
     */
    private final int round;

    /**
     * Results map for players
     */
    private final HashMap<PlayerBean, PlayerResult> playerResults = new HashMap<>();



    /**
     * Constructor
     */
    public RoundResults(int round)
    {
        this.round = round;
    }



    /**
     * Adds result of a player to the round
     *
     * @param result    the PlayerResult
     */
    public void addResult(final PlayerResult result)
    {
        if (result == null)
            throw new IllegalArgumentException("Result cannot be null");

        if (playerResults.containsKey(result.getPlayerBean()))
            throw new IllegalArgumentException("Player " + result.getPlayerBean() + " already answered for this round!");

        playerResults.put(result.getPlayerBean(), result);
    }


    /**
     * Checks if player has answered
     *
     * @param player    the player
     * @return          true, if it has answered
     */
    public boolean hasAnswered(PlayerBean player)
    {
        return playerResults.containsKey(player);
    }

    /**
     * Gets the amount of answers in this round
     */
    public int getAmountOfAnswers()
    {
        return playerResults.size();
    }


    /**
     * Gets result for player
     * @throws IllegalArgumentException if player has not answered or is null
     */
    public PlayerResult getResult(final PlayerBean player) throws IllegalArgumentException
    {
        if (player == null) {
            throw new IllegalArgumentException("Player cannot be null");
        }
        if (!playerResults.containsKey(player)) {
            throw new IllegalArgumentException("Player " + player + " not answered for this round!");
        }
        return playerResults.get(player);
    }


    /**
     * Calculate points for players for this round
     */
    public void calculatePointsForRound()
    {
        // No players answered -> nothing to calculate
        if (playerResults.isEmpty()) {
            return;
        }

        // Number of categories
        int numberOfCategories = playerResults.values().stream()
                .map(PlayerResult::getAnswers)
                .mapToInt(List::size)
                .max()
                .orElse(0); // Um sicherzustellen, dass die Länge korrekt ist

        // Per category
        for (int categoryIndex = 0; categoryIndex < numberOfCategories; categoryIndex++) {

            Map<String, Integer> answerCount = new HashMap<>();
            Map<PlayerBean, String> playerAnswers = new HashMap<>();

            for (PlayerResult playerResult : playerResults.values()) {
                List<String> answers = playerResult.getAnswers();
                String answer = categoryIndex < answers.size() ? answers.get(categoryIndex).toLowerCase().trim() : "";

                // Save answer for player
                playerAnswers.put(playerResult.getPlayerBean(), answer);

                // Count occurrences of answer
                answerCount.put(answer, answerCount.getOrDefault(answer, 0) + 1);
            }

            // Calculate points based on answers
            for (PlayerResult playerResult : playerResults.values()) {
                PlayerBean player = playerResult.getPlayerBean();
                String answer = playerAnswers.get(player);
                int currentPoints = playerResult.getPoints();

                // Keine Antwort: 0 Punkte
                if (answer.isEmpty()) {
                    playerResult.setPoints(currentPoints);

                } else if (answerCount.get(answer) == 1) {

                    // Einzigartige Antwort: 10 Punkte
                    int finalCategoryIndex = categoryIndex;
                    boolean allOthersEmpty = playerResults.values().stream()
                            .filter(p -> !p.getPlayerBean().equals(player))
                            .allMatch(p -> p.getAnswers().get(finalCategoryIndex).isBlank());

                    // Einzige Antwort: 20 Punkte
                    if (allOthersEmpty) {
                        playerResult.setPoints(currentPoints + 20);
                    } else {
                        playerResult.setPoints(currentPoints + 10);
                    }

                } else {

                    // Mehrfache Antwort: 5 Punkte
                    playerResult.setPoints(currentPoints + 5);
                }
            }
        }
    }


    /**
     * Returns all answers in this round
     */
    @Override
    public String toString() {
        return playerResults.values().stream().map(j -> j.getAnswers().toString()).collect(Collectors.joining(", "));
    }
}