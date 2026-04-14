package com.bookmyshow.bmscore.customExceptions;

public class InvalidTheaterIdException extends RuntimeException {
    public InvalidTheaterIdException(String message) {
        super(message);
    }
}
