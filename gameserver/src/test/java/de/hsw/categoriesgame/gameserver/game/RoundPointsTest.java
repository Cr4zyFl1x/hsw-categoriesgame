package de.hsw.categoriesgame.gameserver.game;

import de.hsw.categoriesgame.gameapi.api.PlayerResult;
import de.hsw.categoriesgame.gameapi.api.RoundResults;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RoundPointsTest {

    @Test
    void testPointCalculation() {

        final RoundResults results = new RoundResults(1);
        PlayerBean max = new PlayerBean("Max");
        PlayerBean tim = new PlayerBean("Tim");
        PlayerBean rudolf = new PlayerBean("Rudolf");

        results.addResult(new PlayerResult(max,
                List.of("Hamburg", "Hungary", "H..")));
        results.addResult(new PlayerResult(tim,
                List.of("Hamburg", "Ha ", "")));
        results.addResult(new PlayerResult(rudolf,
                List.of("Hannover", "HA", "")));

        results.calculatePointsForRound();

        assertEquals(35, results.getResult(max).getPoints());
        assertEquals(10, results.getResult(tim).getPoints());
        assertEquals(15, results.getResult(rudolf).getPoints());
    }
}