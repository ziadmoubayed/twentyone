package com.github.ziadmoubayed.twentyone.engine.feedback.input;

public class InvalidChoiceException extends RuntimeException {
    InvalidChoiceException(String choiceStr) {
        super(String.format("Invalid Choice [%s]", choiceStr));
    }
}
