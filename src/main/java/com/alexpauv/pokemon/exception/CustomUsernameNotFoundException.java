package com.alexpauv.pokemon.exception;

public class CustomUsernameNotFoundException extends RuntimeException {
    public CustomUsernameNotFoundException(String message) {
        super(message);
    }
}
