package com.bookmyshow.bmscore.customExceptions;

public class ConfirmPasswordMismatchException extends RuntimeException {
    public ConfirmPasswordMismatchException(String message) {
        super(message);
    }
}
