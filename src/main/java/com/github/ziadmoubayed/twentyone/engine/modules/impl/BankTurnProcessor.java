package com.github.ziadmoubayed.twentyone.engine.modules.impl;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.TurnProcessor;

/**
 * Bank Turn Consumer
 * Bank deals until threshold is reached.
 * It will choose the points that result in the highest score without busting the hand.
 */
public class BankTurnProcessor implements TurnProcessor {

    private final Deck deck;
    private final OutputDriver outputDriver;

    public BankTurnProcessor(Deck deck, OutputDriver outputDriver) {
        this.deck = deck;
        this.outputDriver = outputDriver;
    }

    @Override
    public void accept(Player player) {
        Card card = deck.deal();
        choosePoints(player, player.getHand(), card);
        outputDriver.notifyCardAndPoints(player, card, player.getHand());
    }

    @Override
    public void choosePoints(Player player, Hand hand, Card card) {
        Integer pointsToAdd = null;
        int currentPoints = player.getHand().getPoints();
        for (Integer possiblePoints : card.getPoints()) {
            int sum = currentPoints + possiblePoints;
            if (sum <= 21 && (pointsToAdd == null || sum > pointsToAdd)) {
                pointsToAdd = possiblePoints;
            }
        }
        player.getHand().hit(card, pointsToAdd);
    }
}
