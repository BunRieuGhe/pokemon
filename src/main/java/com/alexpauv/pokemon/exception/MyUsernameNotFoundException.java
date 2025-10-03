package com.alexpauv.pokemon.exception;

public class MyUsernameNotFoundException extends RuntimeException {
    public MyUsernameNotFoundException(String message) {
        super(message);
    }
}
