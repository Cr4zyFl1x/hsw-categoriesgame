package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import de.hsw.categoriesgame.gameapi.rpc.impl.RememberableProxyFactory;
import de.hsw.categoriesgame.gameapi.rpc.impl.SocketRemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import de.hsw.categoriesgame.gameapi.rpc.testres.*;
import org.junit.jupiter.api.*;

import java.lang.reflect.Proxy;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GeneralProxyServerTest {

    private static ITS_Game defaultDomain;
    private static ProxyFactory proxyFactory;


    @BeforeAll
    public static void setUp()
    {
        defaultDomain = new TS_Game();

        final RemoteServer serverServer = new SocketRemoteServer(6444, new DomainRegistry(), defaultDomain);
        serverServer.start();

        final RemoteServer clientServer = new SocketRemoteServer(new DomainRegistry());
        clientServer.start();

        proxyFactory = new RememberableProxyFactory(
                new ConnectionDetails("::1", 6444),
                clientServer);

        sleep(1500);
    }


    @AfterEach
    void after()
    {
        sleep(500);
    }



    @Test
    void testGetDefaultDomain()
    {
        // Test create game
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        assertNotNull(game);
        assertInstanceOf(Proxy.class, game);
    }

    @Test
    void testGetProxyInReturn()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        assertNotNull(game);
        assertInstanceOf(Proxy.class, game);

        final ITS_Lobby lobby = game.createLobby();
        assertNotNull(lobby);
        assertInstanceOf(Proxy.class, lobby);
    }

    @Test
    void testGetListOfProxiesInReturn()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        assertNotNull(game);
        assertInstanceOf(Proxy.class, game);

        final ITS_Lobby lobby = game.createLobby();
        assertNotNull(lobby);
        assertInstanceOf(Proxy.class, lobby);

        game.joinLobby(lobby, List.of(new TS_Player("MAX"), new TS_Player("LEA"), new TS_Player("ANNA")));

        final List<ITS_Player> players = lobby.getPlayers();
        assertNotNull(players);
        assertEquals(3, players.size());
        assertEquals(TS_Player.class, players.get(0).getClass());
    }

    @Test
    void testProxiesEquals()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);

        final ITS_Lobby lobby = game.createLobby();
        final ITS_Lobby lobby2 = game.getLobby(lobby.getLobbyCode());

        assertEquals(lobby, lobby2);
    }

    @Test
    void testLocalDomainInReturnFromServerProxyAreEqual()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        final ITS_Lobby lobby = game.createLobby();

        final ITS_Player player = new TS_Player("MAX");

        lobby.join(player);
        lobby.join(new TS_Player("LEA"));

        assertInstanceOf(TS_Player.class, lobby.getPlayers().get(0));
        assertEquals(player, lobby.getPlayers().get(0));
        assertNotEquals(player, lobby.getPlayers().get(1));
    }


    @Test
    void testProxyMethodThrowsException()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        final ITS_Lobby lobby = game.createLobby();

        assertDoesNotThrow(() -> game.deleteLobby(lobby));
        Throwable p = assertThrows(TS_CustomException.class, () -> game.deleteLobby(lobby));
        assertTrue(p.getMessage().contains("Lobby not found"));
    }


    @Test
    void testPrimitivesAsProxiesReturnType()
    {
        final ITS_Game game = proxyFactory.createProxy(ITS_Game.class);
        game.createLobby();

        assertInstanceOf(Integer.class, game.getLobbyCount());
        assertEquals(defaultDomain.getLobbyCount(), game.getLobbyCount());
    }

    // TODO: Pruefen ob nur Player als rettype funktioniert

    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////


    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {}
    }
}