package bowling;

import bowling_game.Game;
import bowling_game.gameplay.BowlingGameException;
import bowling_game.gameplay.Round;
import bowling_game.player.Player;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BowlingTest {

    @Test
    public void shouldAddRound() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);

        Round round = game.newRound();

        Assert.assertEquals(game.getCurrentRound(), round);
    }

    @Test
    public void shouldFinishRoundAndBePresentInList() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);
        Round round = game.newRound();
        round.nextThrow(p1, 4);
        round.nextThrow(p1, 4);

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
    public void shouldAddScoreToPlayerBeforeFinishingTheRound() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");

        Game game = Game.newGame(p1, p2);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);

        Assert.assertEquals(5, p1.getPoints());
    }

    @Test
    public void shouldThrowAndHavePointsAddedToRoundSummary() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");

        Game game = Game.newGame(p1, p2);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);

        Assert.assertEquals(5, game.getCurrentRound().getPoints(p1));
    }

    @Test
    public void gettingPointsForPlayerFromRoundSummaryShouldNotBeAffectedByOverallPoints() {
        Player p1 = Player.withName("P1");
        p1.addPoints(20);
        Player p2 = Player.withName("P2");

        Game game = Game.newGame(p1, p2);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);

        Assert.assertEquals(5, game.getCurrentRound().getPoints(p1));
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotAddScoreToNotExistingPlayer() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1);
        Round round = game.newRound();

        round.nextThrow(p2, 4);

        Assert.assertNotEquals(round.getPoints(p2), 4);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotGetScoreToNotExistingPlayer() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1);
        Round round = game.newRound();

        round.getPoints(p2);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotAddMoreThan10PointsInASingleThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 11);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotAddMoreThan10PointsInTwoThrows() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 7);
        game.getCurrentRound().nextThrow(p1, 11);
    }

    @Test
    public void shouldSumPointsFromTwoThrows() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 2);

        Assert.assertEquals(6, p1.getPoints());
    }

    @Test(expected = BowlingGameException.class)
    public void playerShouldNotThrowThreeTimes() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();

        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 2);
        game.getCurrentRound().nextThrow(p1, 1);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotFinalizeBeforeSecondThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.finishRound();
    }

    @Test
    public void shouldFinalizeRoundWhenPlayersHaveFinishedTheirsThrows() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p2, 4);
        game.getCurrentRound().nextThrow(p2, 4);

        game.finishRound();

        Assert.assertTrue(game.getCurrentRound().isFinished());
    }

    @Test
    public void shouldSetStrikeFlagWhenPlayerGets10PointsOnFirstThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();

        game.getCurrentRound().nextThrow(p1, 10);

        Assert.assertTrue(p1.hadStrike());
    }

    @Test
    public void shouldFinishPlayersRoundWhenPlayerGetsStrikeOnFirstThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);

        Assert.assertFalse(game.getCurrentRound().canPlayerThrowInCurrentRound(p1));
    }

    @Test
    public void shouldSetSpareFlagWhenPlayerGetsMaxPointsOnSecondThrow() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        game.newRound();

        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 6);

        Assert.assertTrue(p1.hadSpare());
    }

    @Test
    public void shouldGetBonusPointsWhenPlayerHadStrikeInLastRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 3);
        game.getCurrentRound().nextThrow(p1, 4);

        Assert.assertEquals(24, p1.getPoints()); //10 + 3 + 4 + 7 /10 from 1st round, 3 and 4 for second round, and sum of this two throws
    }

    @Test
    public void shouldGetBonusPointsWhenPlayerHadSpareInLastRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 3);
        game.getCurrentRound().nextThrow(p1, 4);

        Assert.assertEquals(20, p1.getPoints()); //5 + 5 + 3 + 4 + 3 /5 and 5 from 1st round, 3 and 4 for second round, and 3 additional from next throw throws
    }

    @Test
    public void shouldResetStrikeFlagAfterTwoAdditionalThrows() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 3);
        game.getCurrentRound().nextThrow(p1, 4);

        Assert.assertFalse(p1.hadStrike());
    }

    @Test
    public void shouldResetSpareFlagAfterOneAdditionalThrow() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 3);

        Assert.assertFalse(p1.hadSpare());
    }

    @Test
    public void shouldPersistStrikeFlagWhenScoredInNextRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);

        Assert.assertTrue(p1.hadStrike());
    }

    @Test
    public void shouldPersistSpareFlagWhenScoredInNextRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);

        Assert.assertTrue(p1.hadSpare());
    }

    @Test
    public void shouldGetProperAmountOfPointsWhenStrikeIsChained() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);

        Assert.assertEquals(50, p1.getPoints());// 10 on 1st r. 10 + 10 on second r. and 10 + 10 in third
    }

    @Test
    public void shouldGetProperAmountOfPointsWhenSpareIsChained() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);
        game.finishRound();
        game.newRound();
        game.getCurrentRound().nextThrow(p1, 5);
        game.getCurrentRound().nextThrow(p1, 5);

        Assert.assertEquals(40, p1.getPoints());// 5+5 on 1st r. 5 + 5 + 5 on second r. and 5 + 5 + 5 on third
    }

    @Test
    public void shouldGetBonusTwoThrowsWhenPlayerGetStrikeIn10thRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);
        forwardGameWithNRounds(game, 9);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 10);
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 1);

        game.finishRound();

        Assert.assertFalse(game.getCurrentRound().canPlayerThrowInCurrentRound(p1));
    }

    @Test
    public void shouldGetBonusThrowWhenPlayerGetSpareIn10thRound() {
        Player p1 = Player.withName("P1");
        Game game = Game.newGame(p1);
        forwardGameWithNRounds(game, 9);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 6);
        game.getCurrentRound().nextThrow(p1, 4);

        game.finishRound();

        Assert.assertFalse(game.getCurrentRound().canPlayerThrowInCurrentRound(p1));
    }

    @Test(expected = BowlingGameException.class)
    public void shouldThrowWhenPlayerThrewMoreThanExpectedByBonusStrikeRound() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        forwardGameWithNRounds(game, 9);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 6);
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 4);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldThrowWhenPlayerThrewMoreThanExpectedByBonusSpareRound() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        forwardGameWithNRounds(game, 9);

        game.newRound();
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 6);
        game.getCurrentRound().nextThrow(p1, 4);
        game.getCurrentRound().nextThrow(p1, 4);
    }

    @Test
    public void shouldGetRoundNumber() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        forwardGameWithNRounds(game, 5);

        Assert.assertEquals(5, game.getRoundNumber());
    }

    @Test
    public void playerShouldGetZeroPointsWhenRoundIsFinalizedBeforeAddingAScore() {
        Player p1 = Player.withName("P1");
        Player p2 = Player.withName("P2");
        Game game = Game.newGame(p1, p2);
        Round round = game.newRound();

        Assert.assertEquals(round.getPoints(p2), 0);
        Assert.assertEquals(round.getPoints(p1), 0);
    }

    @Test(expected = BowlingGameException.class)
    public void shouldNotFinalizeRightAfterGameStart() {
        Game game = Game.newGame();

        game.finishRound();
    }

    private void forwardGameWithNRounds(Game game, int rounds) {
        List<Player> players = game.getPlayers();
        for (int i = 0; i < rounds; i++) {
            game.newRound();
            for (Player player : players) {
                game.getCurrentRound().nextThrow(player, 0);
                game.getCurrentRound().nextThrow(player, 0);
            }
            game.finishRound();
        }
    }
}
