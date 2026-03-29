package com.bookmyshow.bmscore.customExceptions;

public class ScreenNameAlreadyExistsException extends RuntimeException {
    public ScreenNameAlreadyExistsException(String message) {
        super(message);
    }
}
