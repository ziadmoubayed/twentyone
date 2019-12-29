package com.github.ziadmoubayed.twentyone.io;

import com.github.ziadmoubayed.twentyone.engine.feedback.Messages;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.ConsoleOutputDriver;
import com.github.ziadmoubayed.twentyone.engine.feedback.output.OutputDriver;
import com.github.ziadmoubayed.twentyone.utils.Constants;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class TestConsoleOutputDriver {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    OutputDriver outputDriver;

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        outputDriver = new ConsoleOutputDriver();
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void shouldPrintCorrectly() {
//        System.out.print("hello");
        outputDriver.headers();
        assertEquals(Messages.GREETING, outContent.toString().trim());
    }

}
