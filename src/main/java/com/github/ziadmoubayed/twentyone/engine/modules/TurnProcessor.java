package com.github.ziadmoubayed.twentyone.engine.modules;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;

import java.util.function.Consumer;

public interface TurnProcessor extends Consumer<Player> {
    void choosePoints(Player player, Hand hand, Card card);
}
