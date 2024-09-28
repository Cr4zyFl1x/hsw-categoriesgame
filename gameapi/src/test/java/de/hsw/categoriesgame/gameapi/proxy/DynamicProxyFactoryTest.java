package de.hsw.categoriesgame.gameapi.proxy;

import de.hsw.categoriesgame.gameapi.iface.CategoriesGame;
import de.hsw.categoriesgame.gameapi.iface.Player;
import de.hsw.categoriesgame.gameapi.lobby.Lobby;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class DynamicProxyFactoryTest {

    private Socket sock;
    private DynamicProxyFactory proxyFactory;

    @BeforeEach
    public void setUp() throws Exception {
        sock = new Socket("127.0.0.1", 4703);
        proxyFactory = new DynamicProxyFactoryImpl(sock);
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (sock != null) {
            sock.close();
        }
    }



    @Test
    void test() {

        final CategoriesGame l = proxyFactory.getProxy(CategoriesGame.class);
        l.joinLobby(new Player() {
            private String name = "Hallo";

            @Override
            public String getName() {
                return name;
            }
        }, "ujhdfjd");
    }
}
