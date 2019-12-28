package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.engine.TwentyOne;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.ConsoleInputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.ConsoleOutputDriver;

public class App {

    public static void main(String[] args) {
        new Thread(new TwentyOne(new ConsoleInputDriver(), new ConsoleOutputDriver())).start();
    }
}
