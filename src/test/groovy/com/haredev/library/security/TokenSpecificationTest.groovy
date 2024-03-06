package com.haredev.library.security

import com.haredev.library.TimeManager
import spock.lang.Specification

import java.time.Duration
import java.time.temporal.ChronoUnit

import static com.haredev.library.security.samples.TokenPropertiesSample.secretKey
import static pl.amazingcode.timeflow.TestTime.testInstance

class TokenSpecificationTest extends Specification {

    TokenFacade tokenFacade = new TokenFacade(secretKey)

    def setup() {
        TimeManager.setClock()
    }

    def cleanup() {
        TimeManager.resetClock()
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
        boolean result = tokenFacade.isTokenValid(token, getUsername())

        then:
        result
    }

    def "Should return false if token is expired"() {
        given: "Create expired token"
        def token = buildToken()

        when:
        jumpInMinutesForward(10)
        def result = tokenFacade.isTokenValid(token, getUsername())

        then:
        !result
    }

    private String extractUsername(String token) {
        tokenFacade.extractLogin(token)
    }

    private String buildToken() {
        tokenFacade.buildToken(username)
    }

    private static String getUsername() {
        final String username = "test-user"
        return username;
    }

    private static void jumpInMinutesForward(final Integer minutes) {
        final Duration duration = Duration.of(minutes, ChronoUnit.MINUTES)
        testInstance().fastForward(duration)
    }
}
