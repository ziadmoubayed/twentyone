package com.github.ziadmoubayed.twentyone;

import com.github.ziadmoubayed.twentyone.engine.TwentyOne;
import com.github.ziadmoubayed.twentyone.engine.feedback.input.ConsoleInputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.ConsoleOutputDriver;

public class App {

    public static void main(String[] args) {
        /**
         * Launches the main thread.
         * We are separating the input and output drivers because
         * feedback could come from the console while output could be handled on a screen.
         *
         */
        new Thread(new TwentyOne(new ConsoleInputDriver(), new ConsoleOutputDriver())).start();
    }
}
