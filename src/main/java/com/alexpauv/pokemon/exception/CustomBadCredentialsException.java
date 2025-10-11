package com.alexpauv.pokemon.exception;

public class CustomBadCredentialsException extends RuntimeException {
    public CustomBadCredentialsException(String message) {
        super(message);
    }
}
