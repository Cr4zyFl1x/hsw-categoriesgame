package de.hsw.categoriesgame.gameserver;

import de.hsw.categoriesgame.gameapi.rpc.RemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.SocketRemoteServer;
import de.hsw.categoriesgame.gameapi.rpc.impl.registry.DomainRegistry;
import de.hsw.categoriesgame.gameserver.domain.CategoriesGameImpl;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class GameserverApplication {

    /**
     * Logger
     */
    private final static Logger log = LoggerFactory.getLogger(GameserverApplication.class);

    /**
     * Client connection configuration
     */
    @Getter
    private static ResourceBundle config;



    /**
     * Entrypoint
     */
    public static void main(String[] args) throws IOException
    {
        readConfig();

        final Object defaultDomain = new CategoriesGameImpl();

        final RemoteServer server = new SocketRemoteServer(
                Integer.parseInt(getConfig().getString("server.port")),
                new DomainRegistry(),
                defaultDomain);
        server.start();
    }


    /**
     * Read application.properties from classpath
     */
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
}