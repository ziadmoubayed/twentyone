package com.github.ziadmoubayed.twentyone.engine;

import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import com.github.ziadmoubayed.twentyone.engine.core.GameEngine;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.google.common.collect.Lists;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.github.ziadmoubayed.twentyone.utils.Constants.MAX_PLAYERS_PER_GAME;

public class TwentyOne implements Runnable {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private final InputDriver inputDriver;
    private final OutputDriver outputDriver;

    public TwentyOne(InputDriver inputDriver, OutputDriver outputDriver) {
        this.inputDriver = inputDriver;
        this.outputDriver = outputDriver;
    }

    @Override
    public void run() {
        try {
            outputDriver.headers();
            outputDriver.askForPlayersNames();
            var players = inputDriver.getPlayers();
            var partitions = Lists.partition(players, MAX_PLAYERS_PER_GAME);
            for (int i = 0; i < partitions.size(); i++) {
                executor.submit(new GameEngine(inputDriver, outputDriver, partitions.get(i),
                        new Bank(), new Deck(), "Game: " + (i + 1)));
            }
        } finally {
            executor.shutdown();
        }
    }
}
