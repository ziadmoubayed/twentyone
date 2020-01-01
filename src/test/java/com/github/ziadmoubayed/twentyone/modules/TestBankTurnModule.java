package com.github.ziadmoubayed.twentyone.modules;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import com.github.ziadmoubayed.twentyone.actors.players.Hand;
import com.github.ziadmoubayed.twentyone.actors.players.Player;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.impl.BankTurnProcessor;
import com.github.ziadmoubayed.twentyone.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestBankTurnModule {

    @Mock
    Deck deck;
    @Mock
    OutputDriver outputDriver;
    @Mock
    Hand hand;
    @Mock
    Player player;

    Bank bank;

    @Before
    public void init() {
        bank = new Bank();
    }

    @Test
    public void bankCanHitUntilMaxHitThresholdReached() {
        for (int i = 1; i < Constants.BANK_HIT_THRESHOLD; i++) {
            new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), Card.JACK);
            assertTrue(bank.canHit());
        }
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), Card.JACK);
        assertFalse(bank.canHit());
    }

    @Test
    public void bankShouldChoosePointsThatGiveMostScore() {
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), Card.ACE);
        assertEquals(11, bank.getPoints());
    }

    @Test
    public void bankShouldNotBustItselfWhenChoosingPoints() {
        when(deck.deal()).thenReturn(Card.ACE);
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        assertEquals(12, bank.getPoints());
    }

    @Test
    public void bankShouldNotHitCardIfResultsInBust() {
        when(deck.deal()).thenReturn(Card.SIX);
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        assertEquals(18, bank.getPoints());
        //bank now has 21
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), deck.deal());
        assertEquals(18, bank.getPoints());
    }
}
