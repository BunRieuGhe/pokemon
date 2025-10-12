package com.alexpauv.pokemon.service.auth;

import com.alexpauv.pokemon.dto.auth.PasswordResetConfirmationRequest;
import com.alexpauv.pokemon.dto.auth.PasswordResetDto;
import com.alexpauv.pokemon.dto.auth.PasswordResetInquiryRequest;
import com.alexpauv.pokemon.exception.InvalidPasswordResetTokenException;
import com.alexpauv.pokemon.exception.WeakPasswordException;
import com.alexpauv.pokemon.model.user.User;
import com.alexpauv.pokemon.repository.user.UserRepository;
import com.alexpauv.pokemon.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;

@Service
public class PasswordResetService {
    private static final String PASSWORD_RESET_REQUEST_RESPONSE = "If an account with that email exists, a password reset link has been sent.";

    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties securityProperties;

    private final UserRepository userRepository;

    private final EmailService emailService;

    public PasswordResetService(PasswordEncoder passwordEncoder, SecurityProperties securityProperties, UserRepository userRepository, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.securityProperties = securityProperties;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    private String generatePasswordResetToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private void validatePassword(String password) {
        if (password == null || password.length() < securityProperties.minPasswordLength()) {
            throw new WeakPasswordException("Password must be at least " + securityProperties.minPasswordLength() + " characters long.");
        }

        // Password rules
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);

        if (!hasDigit) {
            throw new WeakPasswordException("Password must contain at least one digit");
        }
    }

    @Transactional
    public PasswordResetDto requestPasswordReset(PasswordResetInquiryRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new PasswordResetDto(PASSWORD_RESET_REQUEST_RESPONSE);
        }

        User user = userOptional.get();

        String token = generatePasswordResetToken();

        user.setPasswordResetToken(token);
        user.setPasswordResetTokenExpiryTime(LocalDateTime.now().plusHours(securityProperties.passwordResetTokenValidityHours()));

        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getEmail(), token);

        return new PasswordResetDto(PASSWORD_RESET_REQUEST_RESPONSE);
    }

    @Transactional
    public PasswordResetDto resetPassword(PasswordResetConfirmationRequest request) {
        User user = userRepository.findByPasswordResetToken(request.getToken()).orElseThrow(() -> new InvalidPasswordResetTokenException("Invalid or expired password reset token."));

        if (!user.isPasswordResetTokenValid()) {
            throw new InvalidPasswordResetTokenException("Password reset token has expired.");
        }

        validatePassword(request.getNewPassword());

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.clearPasswordResetToken();
        user.resetFailedAttempts();

        userRepository.save(user);

        return new PasswordResetDto("Password has been reset successfully.");
    }
}
