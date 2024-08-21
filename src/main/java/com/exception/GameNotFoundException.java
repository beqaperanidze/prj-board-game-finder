package com.exception;

public class GameNotFoundException extends Exception {
    public GameNotFoundException(String message) {
        super(message);
    }
}