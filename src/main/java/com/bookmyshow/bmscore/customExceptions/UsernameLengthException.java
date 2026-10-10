package com.bookmyshow.bmscore.customExceptions;

public class UsernameLengthException extends RuntimeException {
    public UsernameLengthException(String message) {
        super(message);
    }
}
