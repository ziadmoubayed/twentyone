package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.players.PlayersSplitter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.github.ziadmoubayed.twentyone.utils.Constants.MAX_PLAYERS_PER_GAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayersSplit {
    @Mock
    private Player player;

    @Test
    public void createsGamesBasedOnMaxAllowedPlayers() {
        int expectedNumberOfGames = 3;
        int playersToAdd = MAX_PLAYERS_PER_GAME * expectedNumberOfGames;
        List<Player> players = createPlayers(playersToAdd);
        var partitions = new PlayersSplitter().split(players);
        assertThat(partitions, hasSize(expectedNumberOfGames));
    }

    private List<Player> createPlayers(int numberOfPlayers) {
        List<Player> players = new ArrayList<>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            players.add(player);
        }
        return players;
    }
}
