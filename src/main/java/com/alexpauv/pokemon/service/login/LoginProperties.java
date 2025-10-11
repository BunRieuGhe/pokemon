package com.alexpauv.pokemon.service.login;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "login")
public record LoginProperties(int maxLoginAttempts, int lockDurationMinutes) {
}
