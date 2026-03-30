package com.bookmyshow.bmscore.customExceptions;

public class InvalidShowIdException extends RuntimeException {
    public InvalidShowIdException(String message) {
        super(message);
    }
}
