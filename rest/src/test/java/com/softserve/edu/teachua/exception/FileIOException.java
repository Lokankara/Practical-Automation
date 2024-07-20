package com.softserve.edu.teachua.exception;

public class FileIOException extends RuntimeException {
    public FileIOException(String message, Exception e) {
        super(message, e);
    }

    public FileIOException(String message) {
        super(message);
    }
}
