package com.github.ziadmoubayed.twentyone.engine.feedback.output;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;

import java.util.List;
import java.util.Set;

public class ConsoleOutputDriver implements OutputDriver {

    @Override
    public void headers() {
        print("Twenty One");
    }

    @Override
    public void startGame(String gameName) {
        print("Starting %s", gameName);
    }

    @Override
    public void askForPlayersNames() {
        print("Type the players' names separated by semi column");
    }

    @Override
    public void askForPlayerTerms(String name, int points, List<PlayTerms> termsList) {
        print("Player %s's turn. You have %d points. Choose %s", name, points, termsList);
    }

    @Override
    public void notifyError(String message) {
        print(message);
    }

    @Override
    public void notifyBust() {
        print("You are out");
    }

    @Override
    public void notifyChoosePoints(String name, Set<Integer> points) {
        print("What which point you want to use: %s", points);
    }

    @Override
    public void notifyCardAndPoints(Player player, Card card, Hand hand) {
        print("%s got %s and now has score %s", player.getName(), card, hand.getPoints());
    }

    @Override
    public void notifyBankTurn() {
        print("The Bank will play now");
    }

    @Override
    public void notifyWinnerAndLoser(Player winner, Player loser) {
        print("%s won against %s", winner.getName(), loser.getName());
    }

    @Override
    public void notifyCardSplit(Player player) {
        print("%s split pair of %s", player.getName(), player.getHand().getCards().get(0));
    }

    @Override
    public void notifyBankWon() {
        print("Bank won all players");
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
