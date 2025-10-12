package com.alexpauv.pokemon.exception;

public class PasswordResetEmailSendingFailureException extends RuntimeException {
    public PasswordResetEmailSendingFailureException(String message) {
        super(message);
    }
}
