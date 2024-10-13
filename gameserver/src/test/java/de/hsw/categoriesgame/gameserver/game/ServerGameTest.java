package de.hsw.categoriesgame.gameserver.game;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.api.GameRoundState;
import de.hsw.categoriesgame.gameapi.api.PlayerResult;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * @author Florian J. Kleine-Vorholt
 */
@ExtendWith(MockitoExtension.class)
public class ServerGameTest {

    private ServerGame serverGame;

    @Spy
    private ArrayList<Client> clients;

    @Mock
    private Client client1;

    @Mock
    private Client client2;

    @Mock
    private Client client3;

    @Mock
    private Client client4;




    @BeforeEach
    public void setUp()
    {
        this.serverGame = new ServerGame(
                List.of(client1, client2, client3, client4),
                new GameConfigs(5, 4)
        );
    }

    @Test
    void testStartGame()
    {
        // Start game
        assertDoesNotThrow(serverGame::startGame);

        // Check if is started
        assertTrue(serverGame.isStarted());

        // Check if players were notified that game has begun
        verify(client1, times(1)).notifyRoundState(any(), any());
        verify(client2, times(1)).notifyRoundState(any(), any());
        verify(client3, times(1)).notifyRoundState(any(), any());
        verify(client4, times(1)).notifyRoundState(any(), any());

        // Check that game cannot be started again
        Throwable t = assertThrows(IllegalStateException.class, () -> serverGame.startGame());
        assertTrue(t.getMessage().contains("already been started"));
    }


    @Test
    void testSendAnswers()
    {
        // Can not send answer if game not started
        Throwable throwable = assertThrows(IllegalStateException.class,
                () -> serverGame.receivePlayerAnswer(new PlayerResult(
                        new PlayerBean("Max"), List.of("Emsdetten", "Estland", "Ems")
                )));

        // Start game
        serverGame.startGame();

        // Send answer
        assertDoesNotThrow(() -> serverGame.receivePlayerAnswer(new PlayerResult(
                new PlayerBean("Max"), List.of("Emsdetten", "Estland", "Ems"))));

        // Assert that Clients are notified to send their answers
        verify(client1, times(1)).notifyRoundState(eq(GameRoundState.ANSWERS_CLOSED), any());
        verify(client2, times(1)).notifyRoundState(eq(GameRoundState.ANSWERS_CLOSED), any());
        verify(client3, times(1)).notifyRoundState(eq(GameRoundState.ANSWERS_CLOSED), any());
        verify(client4, times(1)).notifyRoundState(eq(GameRoundState.ANSWERS_CLOSED), any());
    }
}