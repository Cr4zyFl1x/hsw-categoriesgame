package de.hsw.categoriesgame.gameserver.gamelogic.rules;

public enum PointRules {
    ONLY_PLAYER_WITH_WORD(20),
    DISTINCT_WORD(10),
    MULTIPLE_WORD(5),
    NO_WORD(0),
    FALSE_WORD(0),
    DOUBTED_WORD(0);

    final int points;

    PointRules(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }
}
