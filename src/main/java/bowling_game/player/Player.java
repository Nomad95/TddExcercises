package bowling_game.player;

import lombok.Getter;

@Getter
public class Player {
    private int points;
    private String name;

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
}
