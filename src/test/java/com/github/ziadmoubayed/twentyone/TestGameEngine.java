package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.PlayTerms;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.core.GameEngine;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.InputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestGameEngine {

    @Mock
    InputDriver inputDriver;
    @Mock
    OutputDriver outputDriver;
    @Mock
    Deck deck;
    @Mock
    Bank bank;

    Player player;

    GameEngine gameEngine;

    @Before
    public void init() {
        player = spy(new Player("test"));
        gameEngine = new GameEngine(inputDriver, outputDriver, new ArrayList<>(List.of(player)), bank, deck, "Demo");
    }

    @Test
    public void shufflesDeckOnStart() {
        new GameEngine(inputDriver, outputDriver, new ArrayList<>(), bank, deck, "Demo").run();
        verify(deck).shuffle();
    }

    @Test
    public void bankShouldWinIfNoPlayersAreStanding() {
        when(player.canHit()).thenReturn(false);
        gameEngine.run();
        verify(outputDriver).notifyBankWon();
    }

    @Test
    public void bankShouldWinIfPlayerHasEqualScore() {
        when(bank.getPoints()).thenReturn(10);
        when(bank.canHit()).thenReturn(false);
        when(inputDriver.getPlayerChoice()).thenReturn(PlayTerms.HIT, PlayTerms.STAND);
        when(deck.deal()).thenReturn(Card.TEN);
        gameEngine.run();
        verify(outputDriver).notifyWinnerAndLoser(bank, player);
    }

    @Test
    public void playerShouldWinIfItHasMorePoints() {
        when(bank.getPoints()).thenReturn(9);
        when(bank.canHit()).thenReturn(false);
        when(inputDriver.getPlayerChoice()).thenReturn(PlayTerms.HIT, PlayTerms.STAND);
        when(deck.deal()).thenReturn(Card.TEN);
        gameEngine.run();
        verify(outputDriver).notifyWinnerAndLoser(player, bank);
    }
}
