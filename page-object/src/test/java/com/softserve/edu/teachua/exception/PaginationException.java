package com.softserve.edu.teachua.exception;

public class PaginationException extends RuntimeException {

    public PaginationException(String... messages) {
        super(String.join(" ", messages));
    }
}
