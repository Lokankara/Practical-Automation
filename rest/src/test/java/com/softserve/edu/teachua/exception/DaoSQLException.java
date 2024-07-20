package com.softserve.edu.teachua.exception;

public class DaoSQLException extends RuntimeException {

    public DaoSQLException(String message) {
        super(message);
    }

    public DaoSQLException(String message, Throwable e) {
        super(message, e);
    }
}
