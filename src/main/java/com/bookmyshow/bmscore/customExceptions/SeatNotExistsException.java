package com.bookmyshow.bmscore.customExceptions;

public class SeatNotExistsException extends RuntimeException {
    public SeatNotExistsException(String message) {
        super(message);
    }
}
