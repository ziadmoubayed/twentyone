package com.github.ziadmoubayed.twentyone.engine.feedback.output;


import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;

import java.util.List;
import java.util.Set;

public interface OutputDriver {
    void headers();
    void askForPlayersNames();
    void notifyPlayerTerms(String name, int points, List<PlayTerms> terms);
    void startGame(String gameName);
    void notifyError(String message);
    void notifyBust();
    void notifyChoosePoints(String name, Set<Integer> points);
    void notifyCardAndPoints(Player player, Card card, Hand hand);
    void notifyBankTurn();
    void notifyGameEnded(String gameName);
    void notifyWinnerAndLoser(Player winner, Player loser);
    void notifyCardSplit(Player player);
}
