package com.softserve.edu.teachua.exceptions;

public class PaginationException extends RuntimeException {

    public PaginationException(String... messages) {
        super(String.join(" ", messages));
    }
}
