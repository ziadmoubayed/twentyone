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
import net.jodah.failsafe.FailsafeExecutor;
import net.jodah.failsafe.Fallback;
import net.jodah.failsafe.RetryPolicy;
import net.jodah.failsafe.event.ExecutionAttemptedEvent;
import net.jodah.failsafe.event.ExecutionCompletedEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerTurnProcessor implements TurnProcessor {

    private final InputDriver inputDriver;
    private final OutputDriver outputDriver;
    private final Deck deck;

    private RetryPolicy<Object> retryPolicy = new RetryPolicy<>()
            .handle(InvalidChoiceException.class)
            .onRetry(this::handleInvalidChoice)
            .onFailure(this::notifyBust)
            .withMaxRetries(3);
    private FailsafeExecutor<Object> failSafeExecutor = Failsafe.with(Fallback.of(PlayTerms.BUST), retryPolicy);

    public PlayerTurnProcessor(InputDriver inputDriver, OutputDriver outputDriver, Deck deck) {
        this.inputDriver = inputDriver;
        this.outputDriver = outputDriver;
        this.deck = deck;
    }

    @Override
    public void accept(Player player) {
        List<PlayTerms> terms = new ArrayList<>(List.of(PlayTerms.HIT, PlayTerms.STAND));
        if (player.canSplit()) {
            terms.add(PlayTerms.SPLIT);
        }
        playHand(player, player.getHand(), terms);
        player.getSplitHand().ifPresent(splitHand -> {
            playHand(player, splitHand, terms);
        });
    }

    private void playHand(Player player, Hand hand, List<PlayTerms> terms) {
        outputDriver.notifyPlayerTerms(player.getName(), hand.getPoints(), terms);
        var choice = failSafeExecutor.get(inputDriver::getPlayerChoice);
        processChoice(player, hand, choice);
    }

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
            default:
                break;
        }
    }

    @Override
    public void choosePoints(Player player, Hand hand, Card card) {
        if (card.getPoints().size() > 1) {
            outputDriver.notifyChoosePoints(player.getName(), card.getPoints());
            var points = failSafeExecutor.get(() -> inputDriver.getPlayerPoints(card.getPoints()));
            hand.hit(card, points);
        } else {
            hand.hit(card, new ArrayList<>(card.getPoints()).get(0));
        }
        outputDriver.notifyCardAndPoints(player, card, hand);
    }

    private void split(Player player) {
        player.split();
        outputDriver.notifyCardSplit(player);
    }

    private void handleInvalidChoice(ExecutionAttemptedEvent<Object> e) {
        outputDriver.notifyError(e.getLastFailure().getMessage());
    }

    private void notifyBust(ExecutionCompletedEvent<Object> ignored) {
        outputDriver.notifyBust();
    }
}
