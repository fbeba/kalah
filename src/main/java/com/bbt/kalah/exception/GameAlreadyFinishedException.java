package com.bbt.kalah.exception;

public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException(final String message) {
        super(message);
    }
}
