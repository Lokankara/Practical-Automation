package com.softserve.edu.teachua.exception;

public class DropdownNotInitializedException extends RuntimeException {
    public DropdownNotInitializedException(String... messages) {
        super(String.join(" ", messages));
    }
}
