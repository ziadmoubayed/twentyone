package com.github.ziadmoubayed.twentyone.modules;

import com.github.ziadmoubayed.twentyone.actors.Card;
import com.github.ziadmoubayed.twentyone.actors.Deck;
import com.github.ziadmoubayed.twentyone.actors.players.Bank;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.engine.modules.impl.BankTurnProcessor;
import com.github.ziadmoubayed.twentyone.utils.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestBankTurnModule {

    @Mock
    Deck deck;
    @Mock
    OutputDriver outputDriver;

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
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), Card.ACE);
        new BankTurnProcessor(deck, outputDriver).choosePoints(bank, bank.getHand(), Card.ACE);
        assertEquals(12, bank.getPoints());
    }
}
