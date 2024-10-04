package de.hsw.categoriesgame.gameserver.gamelogic.services;

import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Lobby;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.LobbyConfigs;
import de.hsw.categoriesgame.gameserver.gamelogic.pojos.Player;
import de.hsw.categoriesgame.gameserver.gamelogic.rules.PointRules;
import de.hsw.categoriesgame.gameserver.gamelogic.services.impl.PlayingServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayingServiceTest {

    private static PlayingServiceImpl gameService;

    @BeforeAll
    static void setup() {
        var player1 = new Player("A");
        var player2 = new Player("B");
        var player3 = new Player("C");
        var player4 = new Player("D");
        var player5 = new Player("E");

        LobbyConfigs configs = new LobbyConfigs("ABC", 3, 3, List.of("Stadt", "Land"));
        Lobby lobby = new Lobby(configs, new Player("A"));
        lobby.setPlayers(List.of(player1, player2, player3, player4, player5));
        gameService = new PlayingServiceImpl(lobby);
    }

    @BeforeEach
    void beforeEach() {
        gameService.getGame().resetPoints();
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
        ArrayList<String> answerName = new ArrayList<>(List.of("Anna", "", "", "", "Ben"));
        ArrayList<String> answerLand = new ArrayList<>(List.of("Angola", "Angola", "Andorra", "", "Belgien"));

        ArrayList<ArrayList<String>> allAnswers = new ArrayList<>();
        allAnswers.add(answerName);
        allAnswers.add(answerLand);

        gameService.setCurrentLetter(currentChar);
        gameService.getGame().resetPoints();
        gameService.evaluateAnswers(currentChar, allAnswers);

        var player1 = gameService.getLobby().getPlayers().get(0); // 20 + 5
        var player2 = gameService.getLobby().getPlayers().get(1); // 5
        var player3 = gameService.getLobby().getPlayers().get(2); // 10
        var player4 = gameService.getLobby().getPlayers().get(3); // 0
        var player5 = gameService.getLobby().getPlayers().get(4); // 0



        assertAll("Evaluate Answers",
                () ->assertEquals(25, gameService.getGame().getCurrentPoints().get(player1)),
                () -> assertEquals(5, gameService.getGame().getCurrentPoints().get(player2)),
                () -> assertEquals(10, gameService.getGame().getCurrentPoints().get(player3)),
                () -> assertEquals(0, gameService.getGame().getCurrentPoints().get(player4)),
                () -> assertEquals(0, gameService.getGame().getCurrentPoints().get(player5))
        );
    }

    @Test
    void getCurrentTopThreePlayersWithPoints() {
        var player1 = new Player("A");
        var player2 = new Player("B");
        var player3 = new Player("C");
        var player4 = new Player("D");
        var player5 = new Player("E");

        LobbyConfigs configs = new LobbyConfigs("ABC", 3, 3, List.of("Stadt", "Land"));
        Lobby lobby = new Lobby(configs, player1);
        lobby.setPlayers(List.of(player1, player2, player3, player4, player5));
        gameService = new PlayingServiceImpl(lobby);

        var game = gameService.getGame();
        game.addPointsForPlayer(player1, PointRules.DISTINCT_WORD);
        game.addPointsForPlayer(player2, PointRules.ONLY_PLAYER_WITH_WORD);
        game.addPointsForPlayer(player3, PointRules.MULTIPLE_WORD);
        game.addPointsForPlayer(player4, PointRules.NO_WORD);
        game.addPointsForPlayer(player5, PointRules.FALSE_WORD);


        var actualCurrentTopThree = gameService.getCurrentTopThreePlayersWithPoints();
        var expected = List.of(
                List.of(2, 20),
                List.of(1, 10),
                List.of(3, 5));
        assertEquals(expected, actualCurrentTopThree);
    }
}