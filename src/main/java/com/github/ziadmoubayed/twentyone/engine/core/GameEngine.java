package com.github.ziadmoubayed.twentyone.engine.core;

import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.impl.BankTurnProcessor;
import com.github.ziadmoubayed.twentyone.engine.modules.impl.PlayerTurnProcessor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Core game engine.
 * 1) Deck is shuffled.
 * 2) Players take turn choosing between HIT, STAND & SPLIT.
 * 3) If at least one player is still standing, Bank takes Turn choosing cards.
 * 4) Winners are chosen.
 */
public class GameEngine implements Runnable {

    private final InputDriver inputDriver;
    private final OutputDriver outputDriver;
    private final List<Player> players;
    private final Bank bank;
    private final String gameName;
    private final Deck deck;

    public GameEngine(InputDriver inputDriver, OutputDriver outputDriver, List<Player> players, Bank bank,
                      Deck deck, String gameName) {
        this.inputDriver = inputDriver;
        this.outputDriver = outputDriver;
        this.players = players;
        this.bank = bank;
        this.deck = deck;
        this.gameName = gameName;
    }

    @Override
    public void run() {
        deck.shuffle();
        outputDriver.startGame(gameName);
        playersTurn();
        //all player are done - banks turn
        if (players.stream().anyMatch(Player::isStanding)) {
            banksTurn();
        }
        chooseWinners();
        outputDriver.notifyGameEnded(gameName);
    }

    /**
     * Turn by turn every player plays until all players are standing or bust
     */
    private void playersTurn() {
        List<Player> playing;
        do {
            playing = players.stream().filter(Player::canHit)
                    .collect(Collectors.toList());
            playing.forEach(new PlayerTurnProcessor(inputDriver, outputDriver, deck)::accept);
        } while (!playing.isEmpty());
    }

    /**
     * Bank hits until it reaches the threshold
     */
    private void banksTurn() {
        outputDriver.notifyBankTurn();
        while (bank.canHit()) {
            new BankTurnProcessor(deck, outputDriver).accept(bank);
        }
    }

    /**
     * Winners are chosen based on the collected points
     */
    private void chooseWinners() {
        if (bank.getHand().isBusted()) {
            players.stream().filter(Player::isStanding)
                    .forEach(player -> outputDriver.notifyWinnerAndLoser(player, bank));
        } else {
            players.stream().filter(Player::isStanding).forEach(this::checkHands);
        }
    }

    /**
     * Compare bank and player hands.
     *
     * @param player
     */
    private void checkHands(Player player) {
        chooseWinner(player, player.getHand());
        player.getSplitHand().ifPresent(splitHand -> chooseWinner(player, splitHand));
    }


    /**
     * Compare bank hand and player hand points to choose winner.
     *
     * @param player
     */
    private void chooseWinner(Player player, Hand hand) {
        if (bank.getPoints() >= hand.getPoints()) {
            outputDriver.notifyWinnerAndLoser(bank, player);
        } else {
            outputDriver.notifyWinnerAndLoser(player, bank);
        }
    }
}