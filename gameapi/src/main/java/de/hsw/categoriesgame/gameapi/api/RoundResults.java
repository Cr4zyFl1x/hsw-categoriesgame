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
public class RoundResults implements Serializable {

    @Getter
    private final int round;
    @Getter
    private final HashMap<PlayerBean, PlayerResult> playerResults = new HashMap<>();


    public RoundResults(int round) {
        this.round = round;
    }

    public void addResult(final PlayerResult result)
    {
        if (result == null)
            throw new IllegalArgumentException("Result cannot be null");

        if (playerResults.containsKey(result.getPlayerBean()))
            throw new IllegalArgumentException("Player " + result.getPlayerBean() + " already answered for this round!");

        playerResults.put(result.getPlayerBean(), result);
    }

    public boolean hasAnswered(PlayerBean player)
    {
        return playerResults.containsKey(player);
    }

    public int getAmountOfAnswers()
    {
        return playerResults.size();
    }

    public List<PlayerBean> getPlayers()
    {
        return playerResults.keySet().stream().toList();
    }

    public List<PlayerResult> getResults()
    {
        return playerResults.values().stream().toList();
    }

    public void calculatePointsForRound() {
        if (playerResults.isEmpty()) {
            return; // Keine Spieler haben geantwortet
        }

        // Anzahl der Kategorien (z.B. Stadt, Land, Fluss = 3 Kategorien)
        int numberOfCategories = playerResults.values().stream()
                .map(PlayerResult::getAnswers)
                .mapToInt(List::size)
                .max()
                .orElse(0); // Um sicherzustellen, dass die Länge korrekt ist

        // Für jede Kategorie (Spalte)
        for (int categoryIndex = 0; categoryIndex < numberOfCategories; categoryIndex++) {
            // Zähle die Antworten pro Kategorie
            Map<String, Integer> answerCount = new HashMap<>();
            Map<PlayerBean, String> playerAnswers = new HashMap<>();

            for (PlayerResult playerResult : playerResults.values()) {
                List<String> answers = playerResult.getAnswers();
                String answer = categoryIndex < answers.size() ? answers.get(categoryIndex).toLowerCase().trim() : "";

                // Speichere die Antwort für den Spieler
                playerAnswers.put(playerResult.getPlayerBean(), answer);

                // Zähle die Häufigkeit der Antwort
                answerCount.put(answer, answerCount.getOrDefault(answer, 0) + 1);
            }

            // Berechne die Punkte basierend auf den Antworten
            for (PlayerResult playerResult : playerResults.values()) {
                PlayerBean player = playerResult.getPlayerBean();
                String answer = playerAnswers.get(player);
                int currentPoints = playerResult.getPoints();

                if (answer.isEmpty()) {
                    // Keine Antwort: 0 Punkte
                    playerResult.setPoints(currentPoints + 0);
                } else if (answerCount.get(answer) == 1) {
                    // Einzigartige Antwort: 10 Punkte
                    int finalCategoryIndex = categoryIndex;
                    boolean allOthersEmpty = playerResults.values().stream()
                            .filter(p -> !p.getPlayerBean().equals(player))
                            .allMatch(p -> p.getAnswers().get(finalCategoryIndex).isBlank());

                    if (allOthersEmpty) {
                        // Einzige Antwort: 20 Punkte
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



    @Override
    public String toString() {
        return playerResults.values().stream().map(j -> j.getAnswers().toString()).collect(Collectors.joining(", "));
    }
}