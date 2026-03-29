package com.bookmyshow.bmscore.customExceptions;

public class TheaterAlreadyExistsException extends RuntimeException {
    public TheaterAlreadyExistsException(String message) {
        super(message);
    }
}
