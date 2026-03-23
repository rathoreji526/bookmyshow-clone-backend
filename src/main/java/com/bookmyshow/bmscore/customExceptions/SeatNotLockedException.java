package com.bookmyshow.bmscore.customExceptions;

public class SeatNotLockedException extends RuntimeException {
    public SeatNotLockedException(String message) {
        super(message);
    }
}
