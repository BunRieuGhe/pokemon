package com.alexpauv.pokemon.service.auth;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties(int maxLoginAttempts, int lockDurationMinutes, long passwordResetTokenValidityHours, int minPasswordLength) {
}
