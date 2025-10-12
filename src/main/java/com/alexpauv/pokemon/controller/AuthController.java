package com.alexpauv.pokemon.controller;

import com.alexpauv.pokemon.dto.auth.LoginDto;
import com.alexpauv.pokemon.dto.auth.LoginRequest;
import com.alexpauv.pokemon.dto.auth.PasswordResetConfirmationRequest;
import com.alexpauv.pokemon.dto.auth.PasswordResetDto;
import com.alexpauv.pokemon.dto.auth.PasswordResetInquiryRequest;
import com.alexpauv.pokemon.dto.auth.RegisterDto;
import com.alexpauv.pokemon.dto.auth.RegisterRequest;
import com.alexpauv.pokemon.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterDto> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(userService.registerUser(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginUser(loginRequest));
    }

    @PostMapping("/password-reset/request")
    public ResponseEntity<PasswordResetDto> requestPasswordReset(@RequestBody PasswordResetInquiryRequest request) {
        return ResponseEntity.ok(userService.requestPasswordReset(request));
    }

    @PostMapping("/password-reset/confirm")
    public ResponseEntity<PasswordResetDto> resetPassword(@RequestBody PasswordResetConfirmationRequest request) {
        return ResponseEntity.ok(userService.resetPassword(request));
    }
}
