package com.alexpauv.pokemon.exception;

public class AccountDeadException extends RuntimeException {
    public AccountDeadException(String message) {
        super(message);
    }
}
