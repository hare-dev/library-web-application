package com.haredev.library.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import spock.lang.Specification
import spock.lang.Unroll

import javax.crypto.SecretKey
import java.time.Clock
import java.time.Instant
import java.time.ZoneId

import static com.haredev.library.security.samples.TokenPropertiesSample.secretKey
import static com.haredev.library.security.samples.TokenPropertiesSample.tokenExpiration
import static java.time.temporal.ChronoUnit.DAYS

class TokenSpecificationTest extends Specification {
    Clock clock = Clock.fixed(Instant.now().plus(5, DAYS), ZoneId.of("UTC"))

    TokenFacade tokenFacade = new TokenFacade(
            secretKey,
            tokenExpiration,
            clock);

    def "Should get username"() {
        given: "Create token"
        def token = buildToken()
        def expect = username

        when: "Extract username from token"
        def result = extractUsername(token)

        then: "Compare with username from token"
        result == expect
    }

    def "Should return true if token is valid"() {
        given: "Create not expired token"
        def token = buildToken()

        when:
        boolean result = tokenFacade.isTokenValid(token, username)

        then:
        result
    }

    @Unroll
    def "Should return false if token isn't valid"() {
        given: "Create expired token"
        def token = Jwts.builder()
                .subject(username)
                .expiration(getOldDate())
                .signWith(getSignInKey(secretKey))
                .compact()
        when:
        def result = tokenFacade.isTokenValid(token, username)

        then:
        !result
    }

    private static SecretKey getSignInKey(String secretKey) {
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey))
    }

    private static Date getOldDate() {
        return Date.from(Instant.now().plus(1, DAYS))
    }

    private String extractUsername(String token) {
        tokenFacade.extractLogin(token)
    }

    private String buildToken() {
        tokenFacade.buildToken(username)
    }

    private static final String username = "test-user"
}
