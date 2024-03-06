package com.haredev.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.amazingcode.timeflow.Time;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenFacade {

    @Value("${token.secretKey}")
    private final String secretKey;

    public String extractLogin(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, String username) {
        final String login = extractLogin(token);
        return login.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getTokenExpiration(token).isBefore(Time.instance().now());
    }

    private Instant getTokenExpiration(String token) {
        return extractAllClaims(token).getExpiration().toInstant();
    }

    public String buildToken(String username) {
        return Jwts
                .builder()
                .subject(username)
                .issuedAt(Date.from(Time.instance().now()))
                .expiration(Date.from(Time.instance().now().plus(10, ChronoUnit.MINUTES)))
                .signWith(
                        getSignInKey(),
                        Jwts.SIG.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
