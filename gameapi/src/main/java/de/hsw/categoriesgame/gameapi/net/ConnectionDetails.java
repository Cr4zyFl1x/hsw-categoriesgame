package de.hsw.categoriesgame.gameapi.net;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ConnectionDetails {

    private final String host;
    private final int port;

    public ConnectionDetails(final String host, final int port) {

        if (host == null || host.isEmpty() || port < 1 || port > 65535) {
            throw new IllegalArgumentException("Host and/or Port is invalid!");
        }

        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
