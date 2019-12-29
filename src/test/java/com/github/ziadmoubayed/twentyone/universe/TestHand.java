package com.github.ziadmoubayed.twentyone.universe;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestHand {

    Hand hand;

    @Before
    public void init() {
        hand = new Hand();
    }

    @Test
    public void shouldHaveZeroPointsAtInitialization() {
        assertEquals(0, hand.getPoints());
    }

    @Test
    public void shouldNotBeBustedAtInitialization() {
        assertFalse(hand.isBusted());
    }

    @Test
    public void shouldBeAbleToHitAtFirst() {
        assertTrue(hand.canHit());
    }

    @Test
    public void shouldNotBeStandingAtFirst() {
        assertFalse(hand.isStanding());
    }

    @Test
    public void standShouldChangeFlag() {
        hand.stand();
        assertTrue(hand.isStanding());
    }

    @Test
    public void cardsMoreThan21ShouldBustTheHand() {
        hand.hit(Card.EIGHT, 8);
        hand.hit(Card.SEVEN, 7);
        hand.hit(Card.SIX, 6);
        hand.hit(Card.ACE, 1);
        assertTrue(hand.isBusted());
    }

    @Test
    public void addingCardsShouldUpdatePoints() {
        assertEquals(0, hand.getPoints());
        hand.hit(Card.EIGHT, 8);
        assertEquals(8, hand.getPoints());
    }

    @Test
    public void cardHitsShouldBeEqualIfTheyHaveSamePointsAndCard() {
        hand.hit(Card.TEN, 10);
        var newHand = new Hand();
        newHand.hit(Card.TEN, 10);
        assertEquals(hand.getHits(), newHand.getHits());
    }
}
