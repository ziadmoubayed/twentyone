package universe;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPlayer {

    Player player;

    @Before
    public void init() {
        player = new Player("test");
    }

    @Test
    public void shouldBeEmptyAtInitialization() {
        assertTrue(player.getSplitHand().isEmpty());
    }

    @Test
    public void splitHandShouldNotBeNullAtInitialization() {
        assertNotNull(player.getSplitHand());
    }

    @Test
    public void handShouldNotBeNullAtInitialization() {
        assertNotNull(player.getHand());
    }

    @Test
    public void shouldBeAbleToHitAtFirst() {
        assertTrue(player.canHit());
    }

    @Test
    public void shouldNotBeAbleToSplitAtFirst() {
        assertFalse(player.canSplit());
    }

    @Test
    public void shouldNotBeStandingAtFirst() {
        assertFalse(player.isStanding());
    }

    @Test
    public void shouldBeAbleToSplitAfterAddingTwoIdentitcalCards() {
        player.getHand().hit(Card.SEVEN, 7);
        player.getHand().hit(Card.SEVEN, 7);
        assertTrue(player.canSplit());
    }

    @Test
    public void splittingShouldSplitCardsIntoTwoHands() {
        player.getHand().hit(Card.SEVEN, 7);
        player.getHand().hit(Card.SEVEN, 7);
        player.split();
        assertEquals(7, player.getHand().getPoints());
        assertTrue(player.getSplitHand().isPresent());
        assertEquals(7, player.getSplitHand().get().getPoints());
    }

    @Test
    public void shouldNotBeAbleToSplitAfterSecondCard() {
        player.getHand().hit(Card.SEVEN, 7);
        player.getHand().hit(Card.SEVEN, 8);
        player.getHand().hit(Card.SEVEN, 8);
        assertFalse(player.canSplit());
    }
}
