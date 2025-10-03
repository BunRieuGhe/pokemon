package com.alexpauv.pokemon.dto;

import com.alexpauv.pokemon.model.User;

public class RegisterDto {
    private String username;

    private String email;

    private String token;

    public RegisterDto(User user, String token) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.token = token;
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
