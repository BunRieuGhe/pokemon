package com.alexpauv.pokemon.exception;

public class CustomAccountLockedException extends RuntimeException {
    public CustomAccountLockedException(String message) {
        super(message);
    }
}
