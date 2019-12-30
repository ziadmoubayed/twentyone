package com.github.ziadmoubayed.twentyone.modules;


import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InvalidChoiceException;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.ConsoleOutputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.impl.PlayerTurnProcessor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Set;

import static com.github.ziadmoubayed.twentyone.utils.Constants.MAX_INVALID_CHOICES;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestPlayerTurnModule {

    @Mock
    Deck deck;
    @Mock
    OutputDriver outputDriver;
    @Mock
    InputDriver inputDriver;

    Player player;

    @Before
    public void init() {
        player = new Player("test");
    }

    @Test
    public void shouldBustPlayerAfterMaxInvalidChoicesForTerms() {
        when(inputDriver.getPlayerChoice()).thenThrow(InvalidChoiceException.class);
        new PlayerTurnProcessor(inputDriver, outputDriver, deck).accept(player);
        assertTrue(player.getHand().isBusted());
        assertFalse(player.canHit());
    }

    @Test
    public void shouldBustPlayerAfterMaxInvalidChoicesForPoints() {
        when(inputDriver.getPlayerChoice()).thenReturn(PlayTerms.HIT);
        when(deck.deal()).thenReturn(Card.ACE);
        when(inputDriver.getPlayerPoints(Set.of(1, 11))).thenThrow(InvalidChoiceException.class);
        new PlayerTurnProcessor(inputDriver, outputDriver, deck).accept(player);
        assertTrue(player.getHand().isBusted());
        assertFalse(player.canHit());
    }

    @Test
    public void shouldIncreasePlayerPointsBasedOnChoice(){
        when(inputDriver.getPlayerChoice()).thenReturn(PlayTerms.HIT);
        when(deck.deal()).thenReturn(Card.ACE);
        when(inputDriver.getPlayerPoints(Set.of(1, 11))).thenReturn(1);
        new PlayerTurnProcessor(inputDriver, outputDriver, deck).accept(player);
        assertEquals(1, player.getHand().getPoints());
        when(inputDriver.getPlayerPoints(Set.of(1, 11))).thenReturn(11);
        new PlayerTurnProcessor(inputDriver, outputDriver, deck).accept(player);
        assertEquals(12, player.getHand().getPoints());
    }

    @Test
    public void shouldStandPlayerChoiceIsStand(){
        when(inputDriver.getPlayerChoice()).thenReturn(PlayTerms.STAND);
        when(deck.deal()).thenReturn(Card.ACE);
        new PlayerTurnProcessor(inputDriver, outputDriver, deck).accept(player);
        assertTrue(player.isStanding());
    }
}
