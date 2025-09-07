package com.alexpauv.pokemon.service;

import com.alexpauv.pokemon.dto.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtService(JwtEncoder jwtEncoder, AuthenticationManager authenticationManager) {
        this.jwtEncoder = jwtEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String generateToken(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
