package bowling_game.player;

import lombok.Getter;

public class Player {
    @Getter
    private int points;
    @Getter
    private String name;
    private boolean strike;
    private boolean spare;


    private Player(String name) {
        this.name = name;
    }

    public static Player withName(String name) {
        return new Player(name);
    }

    public void addPoints(int pointsToAdd) {
        if (pointsToAdd < 0) throw new IllegalArgumentException("cant add negative number of points");
        points += pointsToAdd;
    }

    public boolean hadStrike() {
        return strike;
    }

    public boolean hadSpare() {
        return spare;
    }

    public void setStrikeModifier() {
        strike = true;
    }

    public void setSpareModifier() {
        spare = true;
    }

    public void removeStrikeModifier() {
        strike = false;
    }

    public void removeSpareModifier() {
        spare = false;
    }
}
