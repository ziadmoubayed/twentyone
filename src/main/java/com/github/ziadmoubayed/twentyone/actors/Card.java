package com.github.ziadmoubayed.twentyone.actors;

import java.util.Set;

import static java.util.Set.of;

/**
 * The 13 cards used in the game. Every card has an associated one or more scores.
 */
public enum Card {

    ACE(of(1, 11)),
    TWO(of(2)),
    THREE(of(3)),
    FOUR(of(4)),
    FIVE(of(5)),
    SIX(of(6)),
    SEVEN(of(7)),
    EIGHT(of(8)),
    NINE(of(9)),
    TEN(of(10)),
    JACK(of(1)),
    QUEEN(of(2)),
    KING(of(3));

    private Set<Integer> points;

    Card(Set<Integer> points) {
        this.points = points;
    }

    /**
     * Points that can be used.
     *
     * @return
     */
    public Set<Integer> getPoints() {
        return this.points;
    }
}
