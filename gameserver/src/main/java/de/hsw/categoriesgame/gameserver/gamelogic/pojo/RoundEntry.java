package de.hsw.categoriesgame.gameserver.gamelogic.pojo;


import de.hsw.categoriesgame.gameapi.api.Client;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class RoundEntry {

    @Getter
    private final String category;

    @Getter
    private final Client client;

    @Getter
    @Setter
    private String answer;

    @Getter
    private boolean doubted;

    @Getter
    private final List<Client> doubtedBy;

    public RoundEntry(String category, Client client, String answer) {
        this.category = category;
        this.client = client;
        this.answer = answer;
        this.doubtedBy = new ArrayList<>();
    }

    /**
     * Doubt an answer.
     * @param doubtingClient    player that doubted the answer
     */
    public void doubtAnswer(Client doubtingClient) {
        this.doubted = true;
        doubtedBy.add(doubtingClient);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final RoundEntry other = (RoundEntry) obj;
        return this.category.equals(other.category) && this.client.equals(other.client);
    }
}
