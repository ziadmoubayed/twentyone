package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.engine.TwentyOne;

public class App {

    public static void main(String[] args) {
        new Thread(new TwentyOne()).start();
    }
}
