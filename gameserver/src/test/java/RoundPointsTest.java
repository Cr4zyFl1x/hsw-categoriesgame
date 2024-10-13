import de.hsw.categoriesgame.gameapi.api.PlayerResult;
import de.hsw.categoriesgame.gameapi.api.RoundResults;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class RoundPointsTest {

    @Test
    void test() {

        final RoundResults results = new RoundResults(1);
        results.addResult(new PlayerResult(new PlayerBean("Max"),
                List.of("Hamburg", "Hungary", "H..")));

        results.addResult(new PlayerResult(new PlayerBean("Tim"),
                List.of("Hamburg", "Ha ", "")));

        results.addResult(new PlayerResult(new PlayerBean("Rudolf"),
                List.of("Hannover", "HA", "")));

        results.calculatePointsForRound();

        for (PlayerResult result : results.getResults()) {
            System.out.println(result.getPlayerBean().getName() + ": " + result.getPoints());
        }
    }


}
