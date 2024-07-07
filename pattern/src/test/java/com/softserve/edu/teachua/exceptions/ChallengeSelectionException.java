package com.softserve.edu.teachua.exceptions;

public class ChallengeSelectionException extends RuntimeException {
    public ChallengeSelectionException(String message, ReflectiveOperationException e) {
        super(message, e);
    }
}
