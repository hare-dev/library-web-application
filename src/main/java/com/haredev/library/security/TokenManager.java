package com.haredev.library.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Clock;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
class TokenManager {

    @Value("${token.secretKey}")
    private final String secretKey;

    @Value("${token.expirationTime}")
    private final Long expirationTime;

    private final Clock clock;

    public String extractLogin(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String buildToken(String username) {
        return buildToken(new HashMap<>(), username);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String login = extractLogin(token);
        return login.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getTokenExpiration(token).before(Date.from(Instant.now(clock)));
    }

    private Date getTokenExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String buildToken(Map<String, Object> claims, String username) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now(clock)))
                .expiration(Date.from(Instant.ofEpochMilli(Date.from(Instant.now(clock)).getTime() + expirationTime)))
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
