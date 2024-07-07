package com.softserve.edu.teachua.exceptions;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException(String message) {
        super(message);
    }

    public ComponentNotFoundException(String... messages) {
        super(String.join(" ", messages));
    }
}
