package com.alexpauv.pokemon.service;

import com.alexpauv.pokemon.config.RsaKeyProperties;
import com.alexpauv.pokemon.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final String AUTHORITIES_STRING = "authorities";
    private static final long TOKEN_DURATION_IN_MILLISECONDS = 900000;
    private final RsaKeyProperties rsaKeyProperties;

    public JwtService(RsaKeyProperties rsaKeyProperties) {
        this.rsaKeyProperties = rsaKeyProperties;
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(rsaKeyProperties.publicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private List<String> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get(AUTHORITIES_STRING, List.class);
    }

    private Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        List<String> authorities = user.getAllAuthorities().stream().map(Enum::name).toList();
        claims.put(AUTHORITIES_STRING, authorities);

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_DURATION_IN_MILLISECONDS))
                .signWith(rsaKeyProperties.privateKey(), Jwts.SIG.RS256)
                .compact();
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return extractUsername(token);
    }

    public List<String> getAuthoritiesFromToken(String token) {
        return extractAuthorities(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
