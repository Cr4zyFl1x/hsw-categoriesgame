package de.hsw.categoriesgame.gameapi.rpc;

import de.hsw.categoriesgame.gameapi.net.ConnectionDetails;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ConnectionDetails
     */
    @Getter
    @Setter
    private ConnectionDetails connectionDetails;

    /**
     * Domain identifier of the manageable object
     */
    @Getter
    private final UUID domainUUID;

    /**
     * Class of the manageable object via Proxy
     */
    @Getter
    private final Class<?> clazz;

    /**
     * The proxy object to manage the remote domain object
     * <p>
     *     This must not be set during initialization
     * </p>
     */
    private Proxy proxy;



    /**
     * Creates a new ProxyData POJO
     *
     * @param domainUUID    The UUID of the domain to manage
     * @param clazz         The class of the object to manage
     */
    public ProxyData(final ConnectionDetails connectionDetails, final UUID domainUUID, final Class<?> clazz)
    {
        this.connectionDetails = connectionDetails;
        this.domainUUID = domainUUID;
        this.clazz = clazz;
    }

    /**
     * Creates a new ProxyData POJO
     *
     * @param domainUUID    The UUID of the domain to manage
     * @param clazz         The class of the object to manage
     * @param proxy         The proxy object used to manage the remote domain object
     */
    public ProxyData(final ConnectionDetails connectionDetails, final UUID domainUUID, final Class<?> clazz, final Proxy proxy)
    {
        this(connectionDetails, domainUUID, clazz);
        this.proxy = proxy;
    }



    public Proxy getProxy() throws IllegalStateException
    {
        if (this.proxy == null) {
            throw new IllegalStateException("No proxy available");
        }
        return proxy;
    }

    public void setProxy(final Proxy proxy) throws IllegalStateException
    {
        if (this.proxy != null) {
            throw new IllegalStateException("Proxy already set");
        }
        this.proxy = proxy;
    }


    ///////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ProxyData proxyData = (ProxyData) object;
        return Objects.equals(connectionDetails, proxyData.connectionDetails) && Objects.equals(domainUUID, proxyData.domainUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(connectionDetails, domainUUID);
    }
}