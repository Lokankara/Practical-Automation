package com.softserve.edu.teachua.exception;

public class DropdownOptionNotFoundException extends RuntimeException {
    public DropdownOptionNotFoundException(String... messages) {
        super(String.join(" ", messages));
    }
}
