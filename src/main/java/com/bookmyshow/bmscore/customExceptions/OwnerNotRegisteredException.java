package com.bookmyshow.bmscore.customExceptions;

public class OwnerNotRegisteredException extends RuntimeException {
    public OwnerNotRegisteredException(String message) {
        super(message);
    }
}
