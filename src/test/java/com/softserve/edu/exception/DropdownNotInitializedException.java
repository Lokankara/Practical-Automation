package com.softserve.edu.exception;

public class DropdownNotInitializedException extends RuntimeException {
    public DropdownNotInitializedException(String... messages) {
        super(String.join(" ", messages));
    }
}
