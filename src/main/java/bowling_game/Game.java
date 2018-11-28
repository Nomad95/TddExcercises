package bowling_game;

import bowling_game.gameplay.BowlingGameException;
import bowling_game.gameplay.Throw;
import bowling_game.player.Player;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.unmodifiableList;

public class Game {
    private final List<Player> players;
    private final List<Round> rounds;
    private Round currentRound;

    public static Game newGame(Player... players) {
        ArrayList<Player> playerList = Lists.newArrayList(players);

        return new Game(playerList);
    }

    private Game(List<Player> players) {
        this.players = players;
        this.rounds = new ArrayList<>();
    }

    public Round newRound() {
        if (Objects.nonNull(currentRound) && !currentRound.isFinished()) throw new BowlingGameException("Current round is not finished");
        currentRound = new Round(players);
        return currentRound;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void finishRound() {
        rounds.add(currentRound);
        currentRound.finishRound();
    }

    public List<Object> getRoundSummary() {
        return unmodifiableList(rounds);
    }

    public static class Round {
        private Map<Player, Map<Throw, Integer>> score;

        @Getter
        private boolean finished = false;

        Round(List<Player> players) {
            this.score = new HashMap<>(players.size());
            players.forEach(p -> score.put(p, Round.initRound()));
        }

        void finishRound() {
            finished = true;
        }

        public void addPoints(Player player, int points) {

        }

        public int getPoints(Player player) {
            Map<Throw, Integer> playerScore = score.get(player);
            return playerScore.values().stream().reduce(0, (x, y ) -> x + y);
        }

        private static Map<Throw, Integer> initRound() {
            HashMap<Throw, Integer> map = new HashMap<>();
            map.put(Throw.FIRST, 0);
            map.put(Throw.SECOND, 0);

            return map;
        }
    }



}
