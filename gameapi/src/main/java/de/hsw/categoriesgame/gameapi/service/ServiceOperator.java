package de.hsw.categoriesgame.gameapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @author Florian J. Kleine-Vorholt
 */
public final class ServiceOperator<T> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ServiceOperator.class);
    private T domain;


    private Socket socket;
    private PrintWriter send;
    private BufferedReader receive;

    public ServiceOperator(final T domain, final Socket socket)
    {
        this.domain = domain;
        this.socket = socket;

        try {
            this.send = new PrintWriter(socket.getOutputStream());
            this.receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        try {
            System.out.println(receive.readLine());
            System.out.println(receive.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}