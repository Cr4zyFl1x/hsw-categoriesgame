package de.hsw.categoriesgame.gameapi.mapper;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;

import java.util.HashMap;
import java.util.UUID;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class Mapper {

    private static final HashMap<Client, PlayerBean> clients = new HashMap<>();

    public static PlayerBean map(final Client client)
    {
        if (clients.containsKey(client))
            return clients.get(client);

        final PlayerBean bean = new PlayerBean(client.getUUID(), client.getName());
        bean.setPoints(client.getPoints());

        clients.put(client, bean);

        return bean;
    }
}