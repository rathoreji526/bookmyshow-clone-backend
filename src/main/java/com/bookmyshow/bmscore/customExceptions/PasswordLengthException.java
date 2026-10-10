package com.bookmyshow.bmscore.customExceptions;

public class PasswordLengthException extends RuntimeException {
    public PasswordLengthException(String message) {
        super(message);
    }
}
