package com.github.ziadmoubayed.twentyone.engine.feedback.input;

import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Player;

import java.util.List;
import java.util.Set;

public interface InputDriver {
    List<Player> getPlayers();

    PlayTerms getPlayerChoice();

    int getPlayerPoints(Set<Integer> points);
}
