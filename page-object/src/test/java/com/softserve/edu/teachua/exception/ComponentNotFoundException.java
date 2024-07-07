package com.softserve.edu.teachua.exception;

public class ComponentNotFoundException extends RuntimeException {
    public ComponentNotFoundException(String... messages) {
        super(String.join(" ", messages));
    }
}
