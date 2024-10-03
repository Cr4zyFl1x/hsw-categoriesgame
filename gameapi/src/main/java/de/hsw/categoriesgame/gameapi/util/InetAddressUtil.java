package de.hsw.categoriesgame.gameapi.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class InetAddressUtil {

    public static final String getLongIP(final String host)
    {
        try {
            final InetAddress inet = InetAddress.getByName(host);
            return inet.getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
