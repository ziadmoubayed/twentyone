package com.github.ziadmoubayed.twentyone.engine.modules.impl;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InvalidChoiceException;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.TurnProcessor;
import net.jodah.failsafe.Failsafe;
import net.jodah.failsafe.Fallback;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.event.ExecutionAttemptedEvent;
import net.jodah.failsafe.event.ExecutionCompletedEvent;

import java.util.ArrayList;
import java.util.List;

import static com.github.ziadmoubayed.twentyone.utils.Constants.MAX_INVALID_CHOICES;

/**
 * Player turn starts with choosing between HIT, STAND and SPLIT.
 * If card can yield more than one point than the player chooses which one to add.
 * If Player chooses to split than a new hand is created which he can also deal cards to.
 * A player is bust if he hits points more than the threshold.
 */
public class PlayerTurnProcessor implements TurnProcessor {

    private final InputDriver inputDriver;
    private final OutputDriver outputDriver;
    private final Deck deck;

    private RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .handle(InvalidChoiceException.class)
            .onRetry(this::handleInvalidChoice)
            .onFailure(this::notifyBust)
            .withMaxRetries(MAX_INVALID_CHOICES);

    public PlayerTurnProcessor(InputDriver inputDriver, OutputDriver outputDriver, Deck deck) {
        this.inputDriver = inputDriver;
        this.outputDriver = outputDriver;
        this.deck = deck;
    }

    /**
     * Consumer Implementation.
     *
     * @param player
     */
    @Override
    public void accept(Player player) {
        List<PlayTerms> terms = new ArrayList<>(List.of(PlayTerms.HIT, PlayTerms.STAND));
        if (player.canSplit()) {
            terms.add(PlayTerms.SPLIT);
        }
        playHand(player, player.getHand(), terms);
        player.getSplitHand().ifPresent(splitHand -> playHand(player, splitHand, terms));
    }

    /**
     * Choose from bank terms
     *
     * @param player
     * @param hand
     * @param terms
     */
    private void playHand(Player player, Hand hand, List<PlayTerms> terms) {
        outputDriver.askForPlayerTerms(player.getName(), hand.getPoints(), terms);
        var choice = Failsafe.with(Fallback.of(PlayTerms.BUST), retryPolicy).get(inputDriver::getPlayerChoice);
        processChoice(player, hand, choice);
    }

    /**
     * Process player choice.
     *
     * @param player
     * @param hand
     * @param choice
     */
    private void processChoice(Player player, Hand hand, PlayTerms choice) {
        switch (choice) {
            case HIT:
                choosePoints(player, hand, deck.deal());
                break;
            case STAND:
                hand.stand();
                break;
            case SPLIT:
                split(player);
                break;
            case BUST:
                player.getHand().bust();
                break;
            default:
                break;
        }
    }

    /**
     * If points can have different scores than the player is asked to choose which point to add.
     *
     * @param player
     * @param hand
     * @param card
     */
    @Override
    public void choosePoints(Player player, Hand hand, Card card) {
        if (card.getPoints().size() > 1) {
            outputDriver.notifyChoosePoints(player.getName(), card.getPoints());
            var points = Failsafe.with(Fallback.of(Integer.MAX_VALUE), retryPolicy).get(() -> inputDriver.getPlayerPoints(card.getPoints()));
            hand.hit(card, points);
        } else {
            hand.hit(card, new ArrayList<>(card.getPoints()).get(0));
        }
        if (hand.isBusted()) {
            outputDriver.notifyBust();
        } else {
            outputDriver.notifyCardAndPoints(player, card, hand);
        }
    }

    /**
     * Splits the hands. Adds a new hand to the player called Split Hand.
     * If the player splits than the player will play a turn for each hand.
     *
     * @param player
     */
    private void split(Player player) {
        player.split();
        outputDriver.notifyCardSplit(player);
    }

    /**
     * Event handler for one failed attempt.
     *
     * @param e
     */
    private void handleInvalidChoice(ExecutionAttemptedEvent<Object> e) {
        outputDriver.notifyError(e.getLastFailure().getMessage());
    }

    /**
     * Event handler after all attempts for choice fail.
     *
     * @param ignored
     */
    private void notifyBust(ExecutionCompletedEvent<Object> ignored) {
        outputDriver.notifyBust();
    }
}
