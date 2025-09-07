package com.alexpauv.pokemon.controller;

import com.alexpauv.pokemon.dto.LoginDto;
import com.alexpauv.pokemon.dto.LoginRequest;
import com.alexpauv.pokemon.dto.RegisterRequest;
import com.alexpauv.pokemon.service.JwtService;
import com.alexpauv.pokemon.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        userService.createUser(registerRequest);

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> login(@RequestBody LoginRequest loginRequest) {
        String token = this.jwtService.generateToken(loginRequest);
        return ResponseEntity.ok(new LoginDto(loginRequest.getUsername(), token));
    }
}
