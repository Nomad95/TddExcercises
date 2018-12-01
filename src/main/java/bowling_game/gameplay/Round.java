package bowling_game.gameplay;

import bowling_game.player.Player;
import lombok.Getter;

import java.util.*;

public class Round {
    private Map<Player, Score> score;

    @Getter
    private boolean finished = false;

    public Round(List<Player> players, int roundNumber) {
        this.score = new HashMap<>(players.size());
        players.forEach(p -> score.put(p, Round.initRound(p, roundNumber)));
    }

    public void finishRound() {//dwa finishe źle
        if (isRoundIsNotFinished()) throw new BowlingGameException("Round is not finished. Players can still throw");
        finished = true;
    }

    private boolean isRoundIsNotFinished() {
        return score.values().stream().anyMatch(Score::isCanThrow);
    }

    public void nextThrow(Player player, int points) {
        Score playerScore = getScoreOrThrow(player);
        playerScore.nextThrow(points);
        player.addPoints(points);
    }

    public int getPoints(Player player) {
        Score playerScore = getScoreOrThrow(player);
        return playerScore.getPointsSum();
    }

    private static Score initRound(Player p, int roundNumber) {
        return new Score(p, roundNumber);
    }

    private Score getScoreOrThrow(Player player) {
        return Optional.ofNullable(score.get(player))
                .orElseThrow(() -> new BowlingGameException("Player doesn't exist in this game"));
    }

    public boolean canPlayerThrowInCurrentRound(Player player) {
        return getScoreOrThrow(player).isCanThrow();
    }
}
