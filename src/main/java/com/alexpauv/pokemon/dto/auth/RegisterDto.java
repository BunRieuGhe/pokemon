package com.alexpauv.pokemon.dto.auth;

import com.alexpauv.pokemon.model.user.User;

import java.util.UUID;

public class RegisterDto {
    private UUID uuid;

    private String username;

    private String email;

    private String token;

    public RegisterDto(User user, String token) {
        this.uuid = user.getUuid();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.token = token;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
