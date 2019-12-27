package com.github.ziadmoubayed.twentyone.actors.players;


import com.github.ziadmoubayed.twentyone.actors.Card;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.github.ziadmoubayed.twentyone.utils.Constants.PLAYER_BUST_THRESHOLD;
import static java.util.stream.Collectors.toList;

/**
 * A hand holds the cards dealt.
 */
public class Hand {

    private List<CardHit> hits;
    private boolean standing;

    public Hand() {
        this.hits = new ArrayList<>();
    }

    public Hand(CardHit hit) {
        this();
        this.hits.add(hit);
    }

    /**
     * Get Cards in hand
     *
     * @return
     */
    public List<Card> getCards() {
        return hits.stream()
                .map(CardHit::getCard)
                .collect(toList());
    }

    /**
     * Get CardHit list.
     *
     * @return
     */
    public List<CardHit> getHits() {
        return hits;
    }


    /**
     * Returns the sum of the points from the cards in hand.
     *
     * @return
     */
    public int getPoints() {
        return hits.stream()
                .mapToInt(CardHit::getPoints)
                .sum();
    }

    /**
     * A Hand is busted if the total points collected exceed the BUST threshold.
     *
     * @return
     */
    public boolean isBusted() {
        return getPoints() > PLAYER_BUST_THRESHOLD;
    }

    /**
     * Checks if the standing flag is true
     *
     * @return
     */
    public boolean isStanding() {
        return standing;
    }

    /**
     * Can hit if not busted and not standing
     *
     * @return
     */
    public boolean canHit() {
        return !isBusted() && !isStanding();
    }

    /**
     * Add a card and the chosen points to the hand.
     *
     * @param card
     * @param points
     */
    public void hit(Card card, int points) {
        this.hits.add(new CardHit(card, points));
    }

    /**
     * Change status to standing.
     */
    public void stand() {
        if (!isBusted()) {
            this.standing = true;
        }
    }


    /**
     * Card point tuple
     */
    public static class CardHit {

        private Card card;
        private int points;

        CardHit(Card card, int points) {
            this.card = card;
            this.points = points;
        }

        Card getCard() {
            return card;
        }

        int getPoints() {
            return points;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CardHit cardHit = (CardHit) o;
            return points == cardHit.points &&
                    card == cardHit.card;
        }

        @Override
        public int hashCode() {
            return Objects.hash(card, points);
        }
    }
}
