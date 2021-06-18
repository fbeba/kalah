package com.bbt.kalah.exception;

public class GameDoesNotExistException extends RuntimeException {

    public GameDoesNotExistException(final String message) {
        super(message);
    }
}
