package com.softserve.edu.teachua.exception;

public class DropdownNotFoundException extends RuntimeException {
    public DropdownNotFoundException(String... messages) {
        super(String.join(" ", messages));
    }
}
