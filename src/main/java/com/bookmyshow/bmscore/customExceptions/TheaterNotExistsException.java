package com.bookmyshow.bmscore.customExceptions;

public class TheaterNotExistsException extends RuntimeException {
    public TheaterNotExistsException(String message) {
        super(message);
    }
}
