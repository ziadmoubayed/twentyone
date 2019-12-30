package com.github.ziadmoubayed.twentyone.universe;

import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.utils.Constants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TestDeck {

    Deck deck;

    @Before
    public void init() {
        deck = new Deck();
    }

    @Test
    public void shouldReturnNewCardEveryTime() {
        for (int i = 0; i < Constants.DECK_SIZE * 13; i++) {
            assertNotNull(deck.deal());
        }
    }

    @Test
    public void shufflingShouldChangeCardOrder() {
        var cards = new ArrayList<>(deck.getCards());
        deck.shuffle();
        assertNotEquals(cards, deck.getCards());
    }
}
