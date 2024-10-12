package de.hsw.categoriesgame.gameserver.gamelogic;

import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;

import java.util.HashMap;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RoundResults {

    private final int round;
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
    }

    public boolean hasAnswered(PlayerBean player)
    {
        return playerResults.containsKey(player);
    }

    public int getAmountOfAnswers()
    {
        return playerResults.size();
    }
}