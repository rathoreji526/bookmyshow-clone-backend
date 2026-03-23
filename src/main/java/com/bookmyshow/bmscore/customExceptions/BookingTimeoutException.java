package com.bookmyshow.bmscore.customExceptions;

public class BookingTimeoutException extends RuntimeException {
    public BookingTimeoutException(String message) {
        super(message);
    }
}
