package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestSplit {


    Player player;
    Deck splitDeck;

    @Before
    public void init() {
        player = new Player("ZIAD");
        splitDeck = mock(Deck.class);
        when(splitDeck.deal()).thenReturn(Card.ACE, Card.ACE, Card.ACE, Card.FIVE, Card.TEN, Card.JACK, Card.JACK);
    }

    @Test
    public void canSplitOnlyOnce() {
        player.getHand().hit(splitDeck.deal(), 1);
        player.getHand().hit(splitDeck.deal(), 1);
        player.split();
        assertFalse(player.canSplit());
    }

    @Test
    public void canSplitWhenDealtTwoIdentiticalCardsAtFirst() {
        assertFalse(player.canSplit());
        player.getHand().hit(splitDeck.deal(), 1);
        assertFalse(player.canSplit());
        player.getHand().hit(splitDeck.deal(), 1);
        assertTrue(player.canSplit());
    }

    @Test
    public void canNotSplitASplitDeck() {
        player.getHand().hit(splitDeck.deal(), 1);
        player.getHand().hit(splitDeck.deal(), 1);
        player.split();
        player.getHand().hit(splitDeck.deal(), 1);
        //split hand now has 2 aces again
        assertFalse(player.canSplit());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExecptionIfSplitConditionNotSatisfied() {
        new Player("test").split();
    }
}
