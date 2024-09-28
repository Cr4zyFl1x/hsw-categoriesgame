package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.iface.CategoriesGame;
import de.hsw.categoriesgame.gameapi.service.ServiceOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class GameserverApplication {

    private final static Logger log = LoggerFactory.getLogger(GameserverApplication.class);

    private static ResourceBundle config;


    public static void main(String[] args) throws IOException
    {
        readConfig();

        final CategoriesGame game = new CategoriesGameImpl();

        // Socket for accepting connections from configured port
        try (final ServerSocket serverSocket = new ServerSocket(Integer.parseInt(getConfig().getString("server.port")))) {
            while (true) {
                log.info("Waiting for client connection...");
                log.error("de");
                final Socket socket = serverSocket.accept();
                log.debug("Connection established from {}!", socket.getRemoteSocketAddress());
                final ServiceOperator<CategoriesGame> gameServiceOperator = new ServiceOperator<>(game, socket);
                final Thread operatorThread = new Thread(gameServiceOperator);
                operatorThread.start();
            }
        }
    }



    private static void readConfig()
    {
        try (InputStream inputStream = GameserverApplication.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Could not find application.properties");
            }
            config = new PropertyResourceBundle(inputStream);
        } catch (Exception e) {
            log.error("Error reading application.properties", e);
            System.exit(1);
        }
    }


    public static ResourceBundle getConfig() {
        return config;
    }
}
