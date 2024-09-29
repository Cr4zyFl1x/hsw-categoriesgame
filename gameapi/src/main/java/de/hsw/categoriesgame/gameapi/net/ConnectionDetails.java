package de.hsw.categoriesgame.gameapi.net;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * POJO for holding connection details such as hostname or port
 *
 * @author Florian J. Kleine-Vorholt
 */
@Getter
public final class ConnectionDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Hostname (e.g. IPv4, IPv6, FQDN)
     */
    private final String host;

    /**
     * Port between 1 and 65.536
     */
    private final int port;


    /**
     * Creates a new instance of the {@code ConnectionDetails} pojo
     *
     * @param host  hostname
     * @param port  port
     */
    public ConnectionDetails(final String host, final int port) {

        if (host == null || host.isEmpty() || port < 1 || port > 65535) {
            throw new IllegalArgumentException("Host and/or Port is invalid!");
        }

        this.host = host;
        this.port = port;
    }


    ///////////////////////////////////////////
    ///////////////////////////////////////////


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConnectionDetails that = (ConnectionDetails) object;
        return port == that.port && Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
