package com.github.ziadmoubayed.twentyone.actors;

import com.github.ziadmoubayed.twentyone.utils.Constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.IntStream;


/**
 * A deck is a collection of cards.
 * Every deck has a size. It contains one or more Card Sets.
 */
public class Deck {

    private List<Card> cards;

    public Deck() {
        EnumSet<Card> allCards = EnumSet.allOf(Card.class);
        this.cards = new ArrayList<>();
        IntStream.range(0, Constants.DECK_SIZE).forEach(i -> cards.addAll(allCards));
    }

    /**
     * Shuffles the cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    /**
     * Returns a card from the deck.
     *
     * @return
     */
    public Card deal() {
        //we are assuming that the deck will never be empty
        // because we have a limit of three players
        return cards.remove(cards.size() - 1);
    }

    public List<Card> getCards() {
        return cards;
    }
}