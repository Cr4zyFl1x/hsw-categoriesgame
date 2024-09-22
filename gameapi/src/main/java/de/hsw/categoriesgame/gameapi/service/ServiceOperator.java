package de.hsw.categoriesgame.gameapi.service;

import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ServiceOperator<T> implements Runnable {

    private T domain;
    private Socket socket;

    public ServiceOperator(T domain, Socket socket)
    {
        this.domain = domain;
        this.socket = socket;
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {

    }
}
