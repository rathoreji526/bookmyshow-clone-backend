package com.bookmyshow.bmscore.customExceptions;

public class TransactionAlreadyProcessedException extends RuntimeException {
    public TransactionAlreadyProcessedException(String message) {
        super(message);
    }
}
