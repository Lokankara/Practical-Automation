package com.softserve.edu.teachua.exception;

public class ChallengeSelectionException extends RuntimeException {
    public ChallengeSelectionException(String message, ReflectiveOperationException e) {
        super(message, e);
    }
}
