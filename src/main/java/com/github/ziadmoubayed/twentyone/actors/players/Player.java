package com.github.ziadmoubayed.twentyone.actors.players;


import java.util.Optional;

/**
 * Main actor in the game.
 * A Player has one hand by default. If he splits the default hand, he will have another split hand.
 */
public class Player {

    private final String name;
    private Hand hand;
    private Hand splitHand;

    public Player(String name) {
        this.name = name;
        this.hand = new Hand();
    }

    /**
     * Get Player name
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Splits the cards into two separate hands.
     * When a player gets two identical cards, she can choose to ‘split’.
     * This means that the cards are placed next to each other on the table
     * and the player can play twice, one game per card.
     */
    public void split() {
        if (!canSplit()) {
            throw new IllegalArgumentException("Player is not allowed to split now");
        }
        this.splitHand = new Hand(hand.getHits().remove(0));
    }

    /**
     * Get default Hand.
     *
     * @return
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Get Split Hand. Split hand is an optional value.
     * It may not exist if the user didn't already split his cards.
     *
     * @return
     */
    public Optional<Hand> getSplitHand() {
        return Optional.ofNullable(splitHand);
    }

    /**
     * the standard rule is that if the player is dealt a pair of identically ranked initial cards,
     * known as a pair, the player is allowed to split them into separate hand and ask for a new second card for each
     *
     * @return
     */
    public boolean canSplit() {
        return (hand != null && (hand.getCards().size() == 2 && hand.getCards().get(0) == hand.getCards().get(1)));
    }

    /**
     * A Player can hit, If either the default hand or the split hand can hit.
     * A hand can hit if it is not busted and not standing
     *
     * @return
     */
    public boolean canHit() {
        return (hand != null && hand.canHit()) || (getSplitHand().isPresent() && getSplitHand().get().canHit());
    }

    /**
     * A player is standing if both hands are standing.
     *
     * @return
     */
    public boolean isStanding() {
        return (hand != null && hand.isStanding()) || (getSplitHand().isPresent() && getSplitHand().get().isStanding());
    }
}
