package de.hsw.categoriesgame.gameapi.util;

import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class NetUtil {

    /**
     * Checks if a Server is listening on a specific hostname and port
     *
     * @param host  the hostname
     * @param port  the port
     *
     * @return      true, if port is open on hostname
     */
    public static boolean isListening(final String host, final int port)
    {
        try (final Socket socket = new Socket(host, port)){
            return true;
        } catch (final Exception e){
            return false;
        }
    }
}