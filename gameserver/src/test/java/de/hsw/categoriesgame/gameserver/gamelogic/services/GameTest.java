package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.DoubtedAnswer;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameserver.PlayerImpl;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;
    private Player player4;

    private String category1;
    private String category2;
    private String category3;

    private String a1;
    private String a2;
    private String a3;
    private String a4;
    private String a5;
    private String a6;
    private String a7;
    private String a8;
    private String a9;
    private String a10;
    private String a11;
    private String a12;


    @BeforeEach
    void setUp() {
        player1 = new PlayerImpl("Maria");
        player2 = new PlayerImpl("Josef");
        player3 = new PlayerImpl("Jesus");
        player4 = new PlayerImpl("Patrick");

        category1 = "Stadt";
        category2 = "Land";
        category3 = "Fluss";

        a1 = ("Afkls");
        a2 = ("Aas");
        a3 = ("NAjfk");

        a4 = ("Afkls");
        a5 = ("");
        a6 = ("Ajfk");

        a7 = ("Ae");
        a8 = ("");
        a9 = ("Af");

        a10 = ("Afkls");
        a11 = ("");
        a12 = ("Ag");

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
        game.setCategories(List.of(category1, category2, category3));

        var answersPlayer1 = List.of(
                new NormalAnswer(player1.getUUID(), category1, a1),
                new NormalAnswer(player1.getUUID(), category2, a2),
                new NormalAnswer(player1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPointsOfPlayer(player1));

        var answersPlayer2 = List.of(
                new NormalAnswer(player2.getUUID(), category1, a4),
                new NormalAnswer(player2.getUUID(), category2, a5),
                new NormalAnswer(player2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPointsOfPlayer(player2));

        var answersPlayer3 = List.of(
                new NormalAnswer(player3.getUUID(), category1, a7),
                new NormalAnswer(player3.getUUID(), category2, a8),
                new NormalAnswer(player3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);
        game.evaluateAnswers();

        assertFalse(game.haveAllPlayersAnswered());
        assertEquals(0, game.getCurrentPointsOfPlayer(player3));

        var answersPlayer4 = List.of(
                new NormalAnswer(player4.getUUID(), category1, a10),
                new NormalAnswer(player4.getUUID(), category2, a11),
                new NormalAnswer(player4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);
        game.evaluateAnswers();

        assertTrue(game.haveAllPlayersAnswered());
        assertFalse(game.answersWereDoubted());

        //game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category1, a10, player4.getUUID()));

        game.setAnswersWereDoubted(true);

        game.evaluateAnswers();

        assertAll(
                () -> assertEquals(5 + 20 + 0, game.getCurrentPointsOfPlayer(player1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPointsOfPlayer(player3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player4)));
    }
}