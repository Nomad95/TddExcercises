package bowling;

import bowling_game.Game;
import bowling_game.gameplay.BowlingGameException;
import bowling_game.player.Player;
import org.junit.Assert;
import org.junit.Test;

public class BowlingTest {

    @Test
    public void shouldAddRound() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);

        Game.Round round = game.newRound();

        Assert.assertEquals(game.getCurrentRound(), round);
    }

    @Test
    public void shouldFinishRoundAndBePresentInList() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        Game.Round round = game.newRound();

        game.finishRound();

        Assert.assertTrue(game.getRoundSummary().contains(round));
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotStartNewRoundWhenPriorIsNotFinished() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();

        game.newRound();
    }

    @Test
    public void shouldAddScoreToPlayer() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
    }

    @Test
    public void shouldNotAddScoreToNotExistingPlayer() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        Game.Round round = game.newRound();

        round.addPoints(p1, 4);

        Assert.assertEquals(round.getPoints(p1), 4);
    }

    @Test
    public void shouldSumPointsFromTwoThrows() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);

    }

    @Test
    public void shouldNotFinalizeBeforeSecondThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
    }

    @Test
    public void playerShouldGetZeroPointsWhenRoundIsFinalizedBeforeAddingAScore() {

    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotFinalizeRightAfterGameStart() {
        Game game = Game.newGame();

        game.finishRound();
    }
}
