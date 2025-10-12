package com.alexpauv.pokemon.dto.auth;

public class PasswordResetDto {
    private String message;

    public PasswordResetDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
