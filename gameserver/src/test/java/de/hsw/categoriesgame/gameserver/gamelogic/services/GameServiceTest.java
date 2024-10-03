package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.GameServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ListIterator;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    private static GameService gameService;

    @BeforeAll
    static void setup() {
        var player1 = new Player("A", 10);
        var player2 = new Player("B", 20);
        var player3 = new Player("C", 30);
        var player4 = new Player("D", 0);
        var player5 = new Player("E", 5);

        Lobby lobby = new Lobby("ABC");
        lobby.setPlayers(List.of(player1, player2, player3, player4, player5));
        gameService = new GameServiceImpl(lobby);
    }

    @Test
    void addCategory() {
        String category = "Stadt";
        gameService.addCategory(category);

        assertTrue(gameService.getAllCategories().contains(category));
    }

    @Test
    void removeCategory() {
        List<String> categories = List.of("A", "B", "C");

        gameService.getAllCategories().addAll(categories);
        gameService.removeCategory("A");

        assertFalse(gameService.getAllCategories().contains("A"));
    }

    @Test
    void evaluateAnswers() {
        char currentChar = 'A';
        var answerName = List.of("Anna", "Anna", "Anton", "", "Ben");
        var answerLand = List.of("Angola", "Angola", "Andorra", "", "Belgien");

        var allAnswers = List.of(answerName, answerLand);

        gameService.evaluateAnswers(currentChar, allAnswers);

        var player1 = gameService.getLobby().getPlayers().get(0);
        var player2 = gameService.getLobby().getPlayers().get(1);
        var player3 = gameService.getLobby().getPlayers().get(2);
        var player4 = gameService.getLobby().getPlayers().get(3);
        var player5 = gameService.getLobby().getPlayers().get(4);

        assertAll("Evaluate Answers",
                () ->assertEquals(10, player1.getPoints()),
                () -> assertEquals(10, player2.getPoints()),
                () -> assertEquals(20, player3.getPoints()),
                () -> assertEquals(0, player4.getPoints()),
                () -> assertEquals(0, player5.getPoints())
        );
    }

    @Test
    void getCurrentTopThreePlayersWithPoints() {
        var actualCurrentTopThree = gameService.getCurrentTopThreePlayersWithPoints();
        var expected = List.of(List.of(3, 30), List.of(2, 20), List.of(1, 10));
        assertEquals(expected, actualCurrentTopThree);
    }
}