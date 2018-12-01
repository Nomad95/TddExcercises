package bowling_game.gameplay;

import bowling_game.player.Player;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

import static bowling_game.gameplay.Throw.*;

public class Score {
    private Map<Throw, Integer> scorePerThrow;
    private Throw currentThrow;
    private Player player;
    private int roundNumber;
    @Getter
    private boolean canThrow;

    public Score(Player p, int roundNumber) {
        this.scorePerThrow = new HashMap<>(2);
        this.scorePerThrow.put(FIRST, 0);
        this.scorePerThrow.put(SECOND, 0);
        this.currentThrow = FIRST;
        this.canThrow = true;
        this.player = p;
        this.roundNumber = roundNumber;
    }

    public int getPointsSum() {
        return scorePerThrow.get(FIRST) + scorePerThrow.get(SECOND);
    }

    public void nextThrow(int points) {
        checkIfPlayerCanThrow(points);

        addPointsWhenPlayerHasScoredAStrikeLastRound(points);
        addPointsWhenPlayerHasScoredASpareLastRound(points);

        if (currentThrow == TWO_ADDITIONAL) {
            addPointsForTwoAdditionalRounds(points);
        } else if (currentThrow == ONE_ADDITIONAL) {
            addPointsForOneAdditionalThrow(points);
            return;
        }

        addPointsForStandardRound(points);
    }

    private void checkIfPlayerCanThrow(int points) {
        if (!canThrow) throw new BowlingGameException("Player cant throw anymore in this round!");
        if (points > 10) throw new BowlingGameException("Cant throw more than for 10 pts");
        if (points < 0) throw new BowlingGameException("Cant throw more less than 0 pts");
    }

    private void addPointsWhenPlayerHasScoredAStrikeLastRound(int points) {
        if (player.hadStrike()) {
            player.addPoints(points);
            if (currentThrow == SECOND)
                player.removeSpareModifier();
        }
    }

    private void addPointsWhenPlayerHasScoredASpareLastRound(int points) {
        if (player.hadSpare() && currentThrow == FIRST) {
            player.addPoints(points);
            player.removeSpareModifier();
        }
    }

    private void addPointsForTwoAdditionalRounds(int points) {
        player.addPoints(points);
        currentThrow = ONE_ADDITIONAL;
        checkIfStrikeOccuredInFirstAdditionalRound(points);
    }

    private void addPointsForOneAdditionalThrow(int points) {
        player.addPoints(points);
        canThrow = false;
    }

    private void checkIfStrikeOccuredInFirstAdditionalRound(int points) {
        if (scoredAStrike(points)) {
            canThrow = false;
        }
    }

    private void addPointsForStandardRound(int points) {
        if (currentThrow == FIRST) {
            addPointsForFirstThrow(points);
        } else if (currentThrow == SECOND){
            addPointsForSecondThrow(points);
        }
    }

    private void addPointsForFirstThrow(int points) {
        scorePerThrow.put(FIRST, points);
        currentThrow = SECOND;
        checkIfPlayerHasScoredAStrike(points);
    }

    private void checkIfPlayerHasScoredAStrike(int points) {
        if (scoredAStrike(points)) {
            player.setStrikeModifier();
            checkIfStrikeOccurredInLastRound();
        }
    }

    private void checkIfSpareOccurredInLastRound() {
        if (roundNumber == 10) {
            currentThrow = ONE_ADDITIONAL;
            canThrow = true;
        }
    }

    private void addPointsForSecondThrow(int points) {
        canThrow = false;
        Integer firstThrowPoints = scorePerThrow.get(FIRST);
        checkPointExceeds10(points, firstThrowPoints);
        scorePerThrow.put(SECOND, points);
        checkIfPlayerHasScoredASpare(points, firstThrowPoints);
    }

    private void checkIfPlayerHasScoredASpare(int points, Integer firstThrowPoints) {
        if (scoredASpare(points, firstThrowPoints)) {
            player.setSpareModifier();
            checkIfSpareOccurredInLastRound();
        }
    }

    private void checkIfStrikeOccurredInLastRound() {
        if (roundNumber == 10) {
            currentThrow = TWO_ADDITIONAL;
        } else {
            canThrow = false;
        }
    }

    private boolean scoredAStrike(int points) {
        return points == 10;
    }

    private boolean scoredASpare(int points, Integer firstThrowPoints) {
        return firstThrowPoints + points == 10;
    }

    private void checkPointExceeds10(int points, Integer firstThrowPoints) {
        if (firstThrowPoints + points > 10)
            throw new BowlingGameException("Cant throw for " + points + " points. first throw was " + firstThrowPoints + " so second cannot be larger than " + (10-firstThrowPoints));
    }
}
