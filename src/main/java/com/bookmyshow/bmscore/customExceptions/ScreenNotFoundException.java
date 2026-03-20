package com.bookmyshow.bmscore.customExceptions;

public class ScreenNotFoundException extends RuntimeException{
    public ScreenNotFoundException(String message){
        super(message);
    }
}
