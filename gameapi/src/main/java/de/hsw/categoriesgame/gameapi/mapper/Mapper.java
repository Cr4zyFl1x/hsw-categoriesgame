package de.hsw.categoriesgame.gameapi.mapper;

import de.hsw.categoriesgame.gameapi.api.Client;
import de.hsw.categoriesgame.gameapi.pojo.PlayerBean;

/**
 * @author Florian J. Kleine-Vorholt
 */
public class Mapper {

    public static PlayerBean map(final Client client)
    {
        final PlayerBean bean = new PlayerBean(client.getUUID(), client.getName());
        bean.setPoints(client.getPoints());
        return bean;
    }
}