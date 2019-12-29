package com.github.ziadmoubayed.twentyone.io;

import com.github.ziadmoubayed.twentyone.engine.feedback.input.ConsoleInputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InvalidChoiceException;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestConsoleInputDriver {

    InputDriver inputDriver;

    @Before
    public void init(){

    }

    @Test(expected = InvalidChoiceException.class)
    public void shouldFailIfPlayerChoiceIsNotFromPlayerTerms(){
        String data = "asd";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        inputDriver = new ConsoleInputDriver();
        inputDriver.getPlayerChoice();
    }

    @Test(expected = InvalidChoiceException.class)
    public void shouldFailIfPlayerInputsAWrongNumber(){
        String data = "asd";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        inputDriver = new ConsoleInputDriver();
        inputDriver.getPlayerPoints(Set.of(1, 2));
    }

    @Test()
    public void shouldParsePlayersSeperatedBySemiColumn(){
        String data = "PLAYER1;PLAYER2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        inputDriver = new ConsoleInputDriver();
        var players = inputDriver.getPlayers();
        assertNotNull(players);
        assertEquals(2, players.size());
    }
}
