package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.actors.players.Player;
import org.junit.Test;

public class TestSplit {


    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExecptionIfSplitConditionNotSatisfied() {
        new Player("test").split();
    }
}
