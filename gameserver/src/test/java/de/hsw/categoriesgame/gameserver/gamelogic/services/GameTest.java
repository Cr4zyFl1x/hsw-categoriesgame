package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Player;
import de.hsw.categoriesgame.gameapi.pojo.DoubtedAnswer;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameserver.PlayerImpl;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.RoundEntry;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static Game game;
    private static Player player1;
    private static Player player2;
    private static Player player3;
    private static Player player4;

    private static String category1;
    private static String category2;
    private static String category3;

    private static String a1;
    private static String a2;
    private static String a3;
    private static String a4;
    private static String a5;
    private static String a6;
    private static String a7;
    private static String a8;
    private static String a9;
    private static String a10;
    private static String a11;
    private static String a12;


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

    }

    @Test
    void updateRoundNumber() {
        game = new GameImpl(List.of(player1, player2, player3, player4), new GameConfigs(3, 5));

        game.startNewRound();
        assertEquals(2, game.getRoundNumber());
    }

    @Test
    void evaluateAnswers() {
        game = new GameImpl(List.of(player1, player2, player3, player4), new GameConfigs(3, 5));

        game.startNewRound();
        game.setCategories(List.of(category1, category2, category3));

        var answersPlayer1 = List.of(
                new NormalAnswer(player1.getUUID(), category1, a1),
                new NormalAnswer(player1.getUUID(), category2, a2),
                new NormalAnswer(player1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(player1));

        var answersPlayer2 = List.of(
                new NormalAnswer(player2.getUUID(), category1, a4),
                new NormalAnswer(player2.getUUID(), category2, a5),
                new NormalAnswer(player2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(player2));

        var answersPlayer3 = List.of(
                new NormalAnswer(player3.getUUID(), category1, a7),
                new NormalAnswer(player3.getUUID(), category2, a8),
                new NormalAnswer(player3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(player3));

        var answersPlayer4 = List.of(
                new NormalAnswer(player4.getUUID(), category1, a10),
                new NormalAnswer(player4.getUUID(), category2, a11),
                new NormalAnswer(player4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());


        //game.setAnswersWereDoubted(true);


        assertAll(
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(player1)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(player2)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(player3)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(player4)));
    }

    @Test
    void doubtedOnce() {
        game = new GameImpl(List.of(player1, player2, player3, player4), new GameConfigs(3, 5));

        game.setCategories(List.of(category1, category2, category3));

        game.startNewRound();
        ((GameImpl) game).setCurrentLetter();

        var answersPlayer1 = List.of(
                new NormalAnswer(player1.getUUID(), category1, a1),
                new NormalAnswer(player1.getUUID(), category2, a2),
                new NormalAnswer(player1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(player1));

        var answersPlayer2 = List.of(
                new NormalAnswer(player2.getUUID(), category1, a4),
                new NormalAnswer(player2.getUUID(), category2, a5),
                new NormalAnswer(player2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(player2));

        var answersPlayer3 = List.of(
                new NormalAnswer(player3.getUUID(), category1, a7),
                new NormalAnswer(player3.getUUID(), category2, a8),
                new NormalAnswer(player3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(player3));

        var answersPlayer4 = List.of(
                new NormalAnswer(player4.getUUID(), category1, a10),
                new NormalAnswer(player4.getUUID(), category2, a11),
                new NormalAnswer(player4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());

        //game.startDoubtingRound();

        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category2, a2, player4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category2, a2, player2.getUUID()));

        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category3, a3, player3.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player2.getUUID(), category2, a8, player4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player4.getUUID(), category1, a10, player3.getUUID()));

        var expectedDoubted1 = new RoundEntry(category2, player1, a2);
        expectedDoubted1.doubtAnswer(player4);
        expectedDoubted1.doubtAnswer(player2);

        var expectedDoubted2 = new RoundEntry(category3, player1, a3);
        expectedDoubted1.doubtAnswer(player3);

        var expectedDoubted3 = new RoundEntry(category2, player3, a8);
        expectedDoubted1.doubtAnswer(player4);

        var expectedDoubted4 = new RoundEntry(category1, player4, a10);
        expectedDoubted1.doubtAnswer(player3);

        List<RoundEntry> expected = List.of(
                new RoundEntry(category1, player1, a1),
                expectedDoubted1,
                expectedDoubted2,

                new RoundEntry(category1, player2, a4),
                new RoundEntry(category2, player2, a5),
                new RoundEntry(category3, player2, a6),

                new RoundEntry(category1, player3, a7),
                expectedDoubted3,
                new RoundEntry(category3, player3, a9),

                expectedDoubted4,
                new RoundEntry(category2, player4, a11),
                new RoundEntry(category3, player4, a12));

        game.setAnswersWereDoubted(true);

        game.closeDoubtingRound();

        assertAll(
                () -> assertEquals(5 + 0 + 0, game.getCurrentPointsOfPlayer(player1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPointsOfPlayer(player3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player4)));
    }

    @Test
    void doubtedTwice() {
        game = new GameImpl(List.of(player1, player2, player3, player4), new GameConfigs(3, 5));

        game.setCategories(List.of(category1, category2, category3));

        game.startNewRound();
        ((GameImpl) game).setCurrentLetter();


        var answersPlayer1 = List.of(
                new NormalAnswer(player1.getUUID(), category1, a1),
                new NormalAnswer(player1.getUUID(), category2, a2),
                new NormalAnswer(player1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(player1));

        var answersPlayer2 = List.of(
                new NormalAnswer(player2.getUUID(), category1, a4),
                new NormalAnswer(player2.getUUID(), category2, a5),
                new NormalAnswer(player2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(player2));

        var answersPlayer3 = List.of(
                new NormalAnswer(player3.getUUID(), category1, a7),
                new NormalAnswer(player3.getUUID(), category2, a8),
                new NormalAnswer(player3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(player3));

        var answersPlayer4 = List.of(
                new NormalAnswer(player4.getUUID(), category1, a10),
                new NormalAnswer(player4.getUUID(), category2, a11),
                new NormalAnswer(player4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());



        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category2, a2, player4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category2, a2, player2.getUUID()));

        game.doubtAnswer(new DoubtedAnswer(player1.getUUID(), category3, a3, player3.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player2.getUUID(), category2, a8, player4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(player4.getUUID(), category1, a10, player3.getUUID()));

        var expectedDoubted1 = new RoundEntry(category2, player1, a2);
        expectedDoubted1.doubtAnswer(player4);
        expectedDoubted1.doubtAnswer(player2);

        var expectedDoubted2 = new RoundEntry(category3, player1, a3);
        expectedDoubted1.doubtAnswer(player3);

        var expectedDoubted3 = new RoundEntry(category2, player3, a8);
        expectedDoubted1.doubtAnswer(player4);

        var expectedDoubted4 = new RoundEntry(category1, player4, a10);
        expectedDoubted1.doubtAnswer(player3);

        List<RoundEntry> expected = List.of(
                new RoundEntry(category1, player1, a1),
                expectedDoubted1,
                expectedDoubted2,

                new RoundEntry(category1, player2, a4),
                new RoundEntry(category2, player2, a5),
                new RoundEntry(category3, player2, a6),

                new RoundEntry(category1, player3, a7),
                expectedDoubted3,
                new RoundEntry(category3, player3, a9),

                expectedDoubted4,
                new RoundEntry(category2, player4, a11),
                new RoundEntry(category3, player4, a12));

        game.setAnswersWereDoubted(true);

        game.closeDoubtingRound();

        assertAll(
                () -> assertEquals(5 + 0 + 0, game.getCurrentPointsOfPlayer(player1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPointsOfPlayer(player3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(player4)));
    }
}