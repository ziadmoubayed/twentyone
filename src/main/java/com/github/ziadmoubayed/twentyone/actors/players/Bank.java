package com.github.ziadmoubayed.twentyone.actors.players;


import com.github.ziadmoubayed.twentyone.utils.Constants;

import static com.github.ziadmoubayed.twentyone.utils.Constants.BANK_HIT_THRESHOLD;

public class Bank extends Player {

    public Bank() {
        super(Constants.BANK_NAME);
    }

    public int getPoints() {
        return super.getHand().getPoints();
    }

    /**
     * The bank must hit when it has a total of 16 points
     * or less and must stand with a total of 17 points or more.
     *
     * @return
     */
    @Override
    public boolean canHit() {
        return super.canHit() && getPoints() < BANK_HIT_THRESHOLD;
    }

    /**
     * The bank can't split
     *
     * @return
     */
    @Override
    public boolean canSplit() {
        return false;
    }
}