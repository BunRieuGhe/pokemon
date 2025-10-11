package com.alexpauv.pokemon.dto.auth;

import java.util.UUID;

public class LoginDto {
    private UUID uuid;
    private String username;

    private String token;

    public LoginDto() {}

    public LoginDto(UUID uuid, String username, String token) {
        this.uuid = uuid;
        this.username = username;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
