package com.github.ziadmoubayed.twentyone.universe;

import com.github.ziadmoubayed.twentyone.actors.Card;
import org.junit.Test;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertThat;

public class TestCards {

    @Test
    public void cardsPointsShouldBeCorrect() {
        assertThat(Card.ACE.getPoints(), hasItems(11, 1));
        assertThat(Card.KING.getPoints(), hasItems(3));
        assertThat(Card.QUEEN.getPoints(), hasItems(2));
        assertThat(Card.JACK.getPoints(), hasItems(1));
        assertThat(Card.TEN.getPoints(), hasItems(10));
        assertThat(Card.NINE.getPoints(), hasItems(9));
        assertThat(Card.EIGHT.getPoints(), hasItems(8));
        assertThat(Card.SEVEN.getPoints(), hasItems(7));
        assertThat(Card.SIX.getPoints(), hasItems(6));
        assertThat(Card.FIVE.getPoints(), hasItems(5));
        assertThat(Card.FOUR.getPoints(), hasItems(4));
        assertThat(Card.THREE.getPoints(), hasItems(3));
        assertThat(Card.TWO.getPoints(), hasItems(2));
    }
}
