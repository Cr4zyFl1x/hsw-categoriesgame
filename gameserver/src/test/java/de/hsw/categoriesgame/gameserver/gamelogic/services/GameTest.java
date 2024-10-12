package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.DoubtedAnswer;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.NormalAnswer;
import de.hsw.categoriesgame.gameserver.ClientImpl;
import de.hsw.categoriesgame.gameserver.gamelogic.pojo.RoundEntry;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private static Game game;
    private static Client client1;
    private static Client client2;
    private static Client client3;
    private static Client client4;

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
        client1 = new ClientImpl("Maria");
        client2 = new ClientImpl("Josef");
        client3 = new ClientImpl("Jesus");
        client4 = new ClientImpl("Patrick");

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
        game = new GameImpl(List.of(client1, client2, client3, client4), new GameConfigs(3, 5));

        game.startNewRound();
        assertEquals(2, game.getRoundNumber());
    }

    @Test
    void evaluateAnswers() {
        game = new GameImpl(List.of(client1, client2, client3, client4), new GameConfigs(3, 5));

        game.startNewRound();
        game.setCategories(List.of(category1, category2, category3));

        var answersPlayer1 = List.of(
                new NormalAnswer(client1.getUUID(), category1, a1),
                new NormalAnswer(client1.getUUID(), category2, a2),
                new NormalAnswer(client1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(client1));

        var answersPlayer2 = List.of(
                new NormalAnswer(client2.getUUID(), category1, a4),
                new NormalAnswer(client2.getUUID(), category2, a5),
                new NormalAnswer(client2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(client2));

        var answersPlayer3 = List.of(
                new NormalAnswer(client3.getUUID(), category1, a7),
                new NormalAnswer(client3.getUUID(), category2, a8),
                new NormalAnswer(client3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(client3));

        var answersPlayer4 = List.of(
                new NormalAnswer(client4.getUUID(), category1, a10),
                new NormalAnswer(client4.getUUID(), category2, a11),
                new NormalAnswer(client4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());


        //game.setAnswersWereDoubted(true);


        assertAll(
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(client1)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(client2)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(client3)),
                () -> assertEquals(0, game.getCurrentPointsOfPlayer(client4)));
    }

    @Test
    void doubtedOnce() {
        game = new GameImpl(List.of(client1, client2, client3, client4), new GameConfigs(3, 5));

        game.setCategories(List.of(category1, category2, category3));

        game.startNewRound();
        ((GameImpl) game).setCurrentLetter();

        var answersPlayer1 = List.of(
                new NormalAnswer(client1.getUUID(), category1, a1),
                new NormalAnswer(client1.getUUID(), category2, a2),
                new NormalAnswer(client1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(client1));

        var answersPlayer2 = List.of(
                new NormalAnswer(client2.getUUID(), category1, a4),
                new NormalAnswer(client2.getUUID(), category2, a5),
                new NormalAnswer(client2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(client2));

        var answersPlayer3 = List.of(
                new NormalAnswer(client3.getUUID(), category1, a7),
                new NormalAnswer(client3.getUUID(), category2, a8),
                new NormalAnswer(client3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(client3));

        var answersPlayer4 = List.of(
                new NormalAnswer(client4.getUUID(), category1, a10),
                new NormalAnswer(client4.getUUID(), category2, a11),
                new NormalAnswer(client4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());

        //game.startDoubtingRound();

        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category2, a2, client4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category2, a2, client2.getUUID()));

        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category3, a3, client3.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client2.getUUID(), category2, a8, client4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client4.getUUID(), category1, a10, client3.getUUID()));

        var expectedDoubted1 = new RoundEntry(category2, client1, a2);
        expectedDoubted1.doubtAnswer(client4);
        expectedDoubted1.doubtAnswer(client2);

        var expectedDoubted2 = new RoundEntry(category3, client1, a3);
        expectedDoubted1.doubtAnswer(client3);

        var expectedDoubted3 = new RoundEntry(category2, client3, a8);
        expectedDoubted1.doubtAnswer(client4);

        var expectedDoubted4 = new RoundEntry(category1, client4, a10);
        expectedDoubted1.doubtAnswer(client3);

        List<RoundEntry> expected = List.of(
                new RoundEntry(category1, client1, a1),
                expectedDoubted1,
                expectedDoubted2,

                new RoundEntry(category1, client2, a4),
                new RoundEntry(category2, client2, a5),
                new RoundEntry(category3, client2, a6),

                new RoundEntry(category1, client3, a7),
                expectedDoubted3,
                new RoundEntry(category3, client3, a9),

                expectedDoubted4,
                new RoundEntry(category2, client4, a11),
                new RoundEntry(category3, client4, a12));

        game.setAnswersWereDoubted(true);

        game.closeDoubtingRound();

        assertAll(
                () -> assertEquals(5 + 0 + 0, game.getCurrentPointsOfPlayer(client1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(client2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPointsOfPlayer(client3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(client4)));
    }

    @Test
    void doubtedTwice() {
        game = new GameImpl(List.of(client1, client2, client3, client4), new GameConfigs(3, 5));

        game.setCategories(List.of(category1, category2, category3));

        game.startNewRound();
        ((GameImpl) game).setCurrentLetter();


        var answersPlayer1 = List.of(
                new NormalAnswer(client1.getUUID(), category1, a1),
                new NormalAnswer(client1.getUUID(), category2, a2),
                new NormalAnswer(client1.getUUID(), category3, a3));
        game.sendAnswers(answersPlayer1);

        assertEquals(0, game.getCurrentPointsOfPlayer(client1));

        var answersPlayer2 = List.of(
                new NormalAnswer(client2.getUUID(), category1, a4),
                new NormalAnswer(client2.getUUID(), category2, a5),
                new NormalAnswer(client2.getUUID(), category3, a6));
        game.sendAnswers(answersPlayer2);

        assertEquals(0, game.getCurrentPointsOfPlayer(client2));

        var answersPlayer3 = List.of(
                new NormalAnswer(client3.getUUID(), category1, a7),
                new NormalAnswer(client3.getUUID(), category2, a8),
                new NormalAnswer(client3.getUUID(), category3, a9));
        game.sendAnswers(answersPlayer3);

        assertEquals(0, game.getCurrentPointsOfPlayer(client3));

        var answersPlayer4 = List.of(
                new NormalAnswer(client4.getUUID(), category1, a10),
                new NormalAnswer(client4.getUUID(), category2, a11),
                new NormalAnswer(client4.getUUID(), category3, a12));
        game.sendAnswers(answersPlayer4);

        assertFalse(game.answersWereDoubted());



        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category2, a2, client4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category2, a2, client2.getUUID()));

        game.doubtAnswer(new DoubtedAnswer(client1.getUUID(), category3, a3, client3.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client2.getUUID(), category2, a8, client4.getUUID()));
        game.doubtAnswer(new DoubtedAnswer(client4.getUUID(), category1, a10, client3.getUUID()));

        var expectedDoubted1 = new RoundEntry(category2, client1, a2);
        expectedDoubted1.doubtAnswer(client4);
        expectedDoubted1.doubtAnswer(client2);

        var expectedDoubted2 = new RoundEntry(category3, client1, a3);
        expectedDoubted1.doubtAnswer(client3);

        var expectedDoubted3 = new RoundEntry(category2, client3, a8);
        expectedDoubted1.doubtAnswer(client4);

        var expectedDoubted4 = new RoundEntry(category1, client4, a10);
        expectedDoubted1.doubtAnswer(client3);

        List<RoundEntry> expected = List.of(
                new RoundEntry(category1, client1, a1),
                expectedDoubted1,
                expectedDoubted2,

                new RoundEntry(category1, client2, a4),
                new RoundEntry(category2, client2, a5),
                new RoundEntry(category3, client2, a6),

                new RoundEntry(category1, client3, a7),
                expectedDoubted3,
                new RoundEntry(category3, client3, a9),

                expectedDoubted4,
                new RoundEntry(category2, client4, a11),
                new RoundEntry(category3, client4, a12));

        game.setAnswersWereDoubted(true);

        game.closeDoubtingRound();

        assertAll(
                () -> assertEquals(5 + 0 + 0, game.getCurrentPointsOfPlayer(client1)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(client2)),
                () -> assertEquals(10 + 0 + 10, game.getCurrentPointsOfPlayer(client3)),
                () -> assertEquals(5 + 0 + 10, game.getCurrentPointsOfPlayer(client4)));
    }
}