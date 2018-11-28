package bowling;

import bowling_game.player.Player;
import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddNegativeNumberOfPointsToPlayer() {
        Player player = Player.withName("Player 1");
        player.addPoints(-100);
    }

    @Test
    public void shouldAddPointsToPlayer() {
        Player player = Player.withName("Player 1");

        player.addPoints(10);

        Assert.assertEquals(player.getPoints(), 10);
    }
}
