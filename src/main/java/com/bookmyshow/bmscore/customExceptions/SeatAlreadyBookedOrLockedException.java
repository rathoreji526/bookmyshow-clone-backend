package com.bookmyshow.bmscore.customExceptions;

public class SeatAlreadyBookedOrLockedException extends RuntimeException {
    public SeatAlreadyBookedOrLockedException(String message) {
        super(message);
    }
}
