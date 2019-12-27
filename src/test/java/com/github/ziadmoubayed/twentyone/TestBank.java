package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBank {

    Bank bank;

    @Before
    public void init() {
        bank = new Bank();
    }

    @Test
    public void shouldBeEmptyAtInitialization() {
        assertTrue(bank.getSplitHand().isEmpty());
    }

    @Test
    public void splitHandShouldNotBeNullAtInitialization() {
        assertNotNull(bank.getSplitHand());
    }

    @Test
    public void handShouldNotBeNullAtInitialization() {
        assertNotNull(bank.getHand());
    }

    @Test
    public void shouldBeAbleToHitAtFirst() {
        assertTrue(bank.canHit());
    }

    @Test
    public void shouldNotBeAbleToSplitAtFirst() {
        assertFalse(bank.canSplit());
    }

    @Test
    public void shouldNotBeStandingAtFirst() {
        assertFalse(bank.isStanding());
    }

    @Test
    public void pointsShouldBeEqualToZero() {
        assertEquals(0, bank.getPoints());
    }

    @Test
    public void bankCanNotHitAfterThreshold() {
        bank.getHand().hit(Card.TEN, 10);
        bank.getHand().hit(Card.TEN, 10);
        bank.getHand().hit(Card.TEN, 10);
        assertFalse(bank.canHit());
        assertTrue(bank.getHand().isBusted());
        assertFalse(bank.isStanding());
    }
}
