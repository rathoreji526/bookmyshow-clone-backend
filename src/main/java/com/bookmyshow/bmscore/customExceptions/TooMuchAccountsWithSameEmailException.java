package com.bookmyshow.bmscore.customExceptions;

public class TooMuchAccountsWithSameEmailException extends RuntimeException {
    public TooMuchAccountsWithSameEmailException(String message) {
        super(message);
    }
}
