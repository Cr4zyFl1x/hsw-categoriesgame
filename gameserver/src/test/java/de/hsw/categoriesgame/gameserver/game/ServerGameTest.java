package de.hsw.categoriesgame.gameserver.game;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.GameConfigs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Florian J. Kleine-Vorholt
 */
@ExtendWith(MockitoExtension.class)
public class ServerGameTest {

    @InjectMocks
    private ServerGame serverGame;

    @Mock
    private GameConfigs configs;

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
    public void setUp() {
//        clients = List.of(client1, client2, client3, client4);
        clients.add(client1);
    }

    @Test
    void test()
    {
        Mockito.verify(serverGame, Mockito.times(1));
    }
}
