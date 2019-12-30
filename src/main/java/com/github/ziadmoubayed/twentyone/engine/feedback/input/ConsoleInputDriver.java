package com.github.ziadmoubayed.twentyone.engine.feedback.input;

import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.utils.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

public class ConsoleInputDriver implements InputDriver {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public List<Player> getPlayers() {
        var choiceStr = readInput();
        try {
            return Arrays.stream(choiceStr.split(Constants.PLAYER_DELIMITER))
                    .map(Player::new).collect(Collectors.toList());
        } catch (Exception e) {
            throw new InvalidChoiceException(choiceStr);
        }
    }

    @Override
    public PlayTerms getPlayerChoice() {
        var choiceStr = readInput();
        try {
            return PlayTerms.valueOf(choiceStr);
        } catch (IllegalArgumentException e) {
            throw new InvalidChoiceException(choiceStr);
        }
    }

    @Override
    public int getPlayerPoints(Set<Integer> points) {
        var choiceStr = readInput();
        try {
            var choice = Integer.valueOf(choiceStr);
            if (points.contains(choice)) return choice;
            else throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new InvalidChoiceException(choiceStr);
        }
    }

    private String readInput() {
        return scanner.nextLine();
    }
}