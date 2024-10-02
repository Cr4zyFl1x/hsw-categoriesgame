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
    private String host;

    /**
     * Port between 1 and 65.536
     */
    private int port;


    /**
     * Creates a new instance of the {@code ConnectionDetails} pojo
     *
     * @param host  hostname
     * @param port  port
     */
    public ConnectionDetails(final String host, final int port) {

        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port/Hostname is invalid!");
        }

        this.host = host;
        this.port = port;
    }

    public ConnectionDetails(final String host) {
        if (host == null || host.isBlank()) {
            throw new IllegalArgumentException("Hostname is invalid!");
        }
        this.host = host;
    }

    public ConnectionDetails(final int port) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port is invalid!");
        }
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
