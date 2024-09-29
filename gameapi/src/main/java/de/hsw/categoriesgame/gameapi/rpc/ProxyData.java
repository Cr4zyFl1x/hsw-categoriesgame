package de.hsw.categoriesgame.gameapi.rpc;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ProxyData implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Domain identifier of the manageable object
     */
    private final UUID domainUUID;

    /**
     * Class of the manageable object via Proxy
     */
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
    public ProxyData(final UUID domainUUID, final Class<?> clazz)
    {
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
    public ProxyData(final UUID domainUUID, final Class<?> clazz, final Proxy proxy)
    {
        this(domainUUID, clazz);
        this.proxy = proxy;
    }



    public UUID getDomainUUID() {
        return domainUUID;
    }


    public Class<?> getClazz() {
        return clazz;
    }


    public Proxy getProxy() throws IllegalStateException {
        if (proxy == null) {
            throw new IllegalStateException("No proxy available");
        }
        return proxy;
    }


    public void setProxy(final Proxy proxy) throws IllegalStateException {
        if (proxy != null) {
            throw new IllegalStateException("Proxy already set");
        }
        this.proxy = proxy;
    }
}
