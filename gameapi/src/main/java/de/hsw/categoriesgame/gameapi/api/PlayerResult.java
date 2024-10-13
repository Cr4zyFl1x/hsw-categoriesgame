package de.hsw.categoriesgame.gameapi.api;

import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import lombok.Getter;

import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class PlayerResult {

    @Getter
    private final PlayerBean playerBean;

    @Getter
    private final List<String> answers;


    public PlayerResult(PlayerBean playerBean, List<String> answers) {
        this.playerBean = playerBean;
        this.answers = answers;
    }
}