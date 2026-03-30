package com.bookmyshow.bmscore.customExceptions;

public class InactiveScreenException extends RuntimeException {
    public InactiveScreenException(String message) {
        super(message);
    }
}
