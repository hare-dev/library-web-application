package com.haredev.library.security

import pl.amazingcode.timeflow.TestTime
import pl.amazingcode.timeflow.Time
import spock.lang.Specification

import java.time.Clock
import java.time.Duration
import java.time.ZoneId
import java.time.temporal.ChronoUnit

import static com.haredev.library.security.samples.TokenPropertiesSample.secretKey

class TokenSpecificationTest extends Specification {

    TokenFacade tokenFacade = new TokenFacade(secretKey)

    def cleanup() {
        TestTime.testInstance().resetClock()
    }

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

    def "Should return false if token is expired"() {
        given: "Create expired token"
        TestTime.testInstance().setClock(FIXED_CLOCK)
        var duration = Duration.of(20, ChronoUnit.MINUTES);
        def token = buildToken()

        when:
        TestTime.testInstance().fastForward(duration)
        def result = tokenFacade.isTokenValid(token, username)

        then:
        !result
    }

    private String extractUsername(String token) {
        tokenFacade.extractLogin(token)
    }

    private String buildToken() {
        tokenFacade.buildToken(username)
    }

    private static final String username = "test-user"

    private final ZoneId ZONE_ID = TimeZone.getTimeZone("Europe/Warsaw").toZoneId();
    private final FIXED_CLOCK = Clock.fixed(Time.instance().now(), ZONE_ID)
}
