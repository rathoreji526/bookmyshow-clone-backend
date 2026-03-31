package com.bookmyshow.bmscore.customExceptions;

public class OverlappingShowsException extends RuntimeException {
    public OverlappingShowsException(String message) {
        super(message);
    }
}
