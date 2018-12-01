package bowling_game;

import bowling_game.gameplay.BowlingGameException;
import bowling_game.gameplay.Round;
import bowling_game.gameplay.Throw;
import bowling_game.player.Player;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.*;

import static java.util.Collections.unmodifiableList;

public class Game {
    private final List<Player> players;
    private final List<Round> rounds;
    private Round currentRound;
    @Getter
    private int roundNumber = 0;

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
        if (roundNumber == 10) throw new BowlingGameException("Cant start new round. Current round is last round");
        roundNumber++;
        currentRound = new Round(players, roundNumber);
        return currentRound;
    }

    public Round getCurrentRound() {
        return currentRound;
    }

    public void finishRound() {
        if (Objects.isNull(currentRound)) throw new BowlingGameException("Cant finish game. It has not started yet");
        currentRound.finishRound();
        rounds.add(currentRound);
    }

    public List<Object> getRoundSummary() {
        return unmodifiableList(rounds);
    }

    public List<Player> getPlayers() {
        return unmodifiableList(players);
    }

}
