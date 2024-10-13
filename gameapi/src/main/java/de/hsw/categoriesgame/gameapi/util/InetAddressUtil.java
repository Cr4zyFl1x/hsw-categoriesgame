package de.hsw.categoriesgame.gameapi.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class InetAddressUtil {

    /**
     * Gets the long version of an IP-Adress.
     * <p>
     *     If a hostname like "google.de" is provided the ip address will be returned.
     * </p>
     *
     * @param host  the ip address
     *
     * @return  long ip address
     */
    public static String getLongIP(final String host)
    {
        try {
            final InetAddress inet = InetAddress.getByName(host);
            return inet.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
