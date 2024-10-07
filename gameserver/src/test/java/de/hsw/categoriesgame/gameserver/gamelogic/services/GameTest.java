package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameserver.PlayerImpl;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    @BeforeEach
    void setUp() {
        player1 = new PlayerImpl("Maria");
        player2 = new PlayerImpl("Josef");
        player3 = new PlayerImpl("Jesus");
        player4 = new PlayerImpl("Patrick");

        game = new GameImpl(List.of(player1, player2, player3, player4));
    }

    @Test
    void updateRoundNumber() {
        game.updateRoundNumber();
        assertEquals(2, game.getRoundNumber());
    }

    @Test
    void evaluateAnswers() {
        ((GameImpl) game).setCurrentLetter('A');
        game.setCategories(List.of("Stadt", "Land", "Fluss"));

        var answersPlayer1 = Map.of("Stadt", "Afkls", "Land", "Aas", "Fluss", "NAjfk");
        game.setAnswersOfPlayer(player1, answersPlayer1);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPoints().get(player1));

        var answersPlayer2 = Map.of("Stadt", "Afkls", "Land", "", "Fluss", "Ajfk");
        game.setAnswersOfPlayer(player2, answersPlayer2);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPoints().get(player2));

        var answersPlayer3 = Map.of("Stadt", "Ae", "Land", "", "Fluss", "Af");
        game.setAnswersOfPlayer(player3, answersPlayer3);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPoints().get(player3));

        var answersPlayer4 = Map.of("Stadt", "Afkls", "Land", "", "Fluss", "Ag");
        game.setAnswersOfPlayer(player4, answersPlayer4);
        game.evaluateAnswers();

        assertTrue(game.haveAllPlayersAnswered());
        assertAll(
                () -> assertEquals(5 + 20 + 0, game.getCurrentPoints().get(player1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPoints().get(player2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPoints().get(player3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPoints().get(player4)));

    }
}