package com.github.ziadmoubayed.twentyone.engine.feedback.output;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import static com.github.ziadmoubayed.twentyone.engine.feedback.Messages.*;

import java.util.List;
import java.util.Set;

public class ConsoleOutputDriver implements OutputDriver {

    @Override
    public void headers() {
        print(GREETING);
    }

    @Override
    public void askForPlayersNames() {
        print(PLAYER_NAMES);
    }

    @Override
    public void notifyPlayerTerms(String name, int points, List<PlayTerms> termsList) {
        print(PLAYER_TURN, name, points, termsList);
    }

    @Override
    public void startGame(String gameName) {
        print(START_GAME, gameName);
    }

    @Override
    public void notifyError(String message) {
        print(message);
    }

    @Override
    public void notifyBust() {
        print(NOTIFY_BUST);
    }

    @Override
    public void notifyChoosePoints(String name, Set<Integer> points) {
        print(NOTIFY_CHOOSE_POINTS, points);
    }

    @Override
    public void notifyCardAndPoints(Player player, Card card, Hand hand) {
        print(NOTIFY_CARD_POINTS, player.getName(), card, hand.getPoints());
    }

    @Override
    public void notifyBankTurn() {
        print(NOTIFY_BANK_TURN);
    }

    @Override
    public void notifyGameEnded(String gameName) {
        print(NOTIFY_GAME_ENDED, gameName);
    }

    @Override
    public void notifyWinnerAndLoser(Player winner, Player loser) {
        print(NOTIFY_WINNER_LOSER, winner.getName(), loser.getName());
    }

    @Override
    public void notifyCardSplit(Player player) {
        print(NOTIFY_CARD_SPLIT, player.getName(), player.getHand().getCards().get(0));
    }

    @Override
    public void notifyBankWon() {
        print(NOTIFY_BANK_WON);
    }

    /**
     * Helper method to output formatted text to the console
     *
     * @param message
     * @param params
     */
    private void print(String message, Object... params) {
        System.out.println(String.format(message, params));
    }
}
