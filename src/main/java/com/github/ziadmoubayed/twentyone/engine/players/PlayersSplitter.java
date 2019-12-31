package com.github.ziadmoubayed.twentyone.engine.players;

import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.google.common.collect.Lists;

import java.util.List;

import static com.github.ziadmoubayed.twentyone.utils.Constants.MAX_PLAYERS_PER_GAME;

/**
 * Player Splitter is a basic game splitter.
 * We can override the splitter logic
 * and create different splitting algorithms like round robin or random splitting.
 */
public class PlayersSplitter {

    public List<List<Player>> split(List<Player> players) {
        return Lists.partition(players, MAX_PLAYERS_PER_GAME);
    }
}
