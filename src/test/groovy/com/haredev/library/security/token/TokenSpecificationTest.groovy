package com.haredev.library.security.token

import com.haredev.library.configuration.time.TestTimeProvider
import com.haredev.library.security.authentication.token.TokenFacade
import spock.lang.Specification

import static com.haredev.library.security.token.samples.AuthenticationTokenPropertiesSample.secretKey
import static com.haredev.library.security.token.samples.AuthenticationTokenPropertiesSample.tokenExpiration

class TokenSpecificationTest extends Specification implements TestTimeProvider {
    final def tokenFacade = new TokenFacade(secretKey, tokenExpiration)

    def setup() {
        setClock()
    }

    def cleanup() {
        resetClock()
    }

    def "Should get username"() {
        given: "Create token"
        def token = buildToken()
        def username = getUsername()

        when: "Extract username from token"
        def expect_username = extractUsername(token)

        then: "Compare with username from token"
        expect_username == username
    }

    def "Should return true if token is valid"() {
        given: "Create token"
        def token = buildToken()
        def expect_result = true

        when: "Validate token"
        boolean result = tokenFacade.isTokenValid(token, getUsername())

        then: "Token is not expired"
        expect_result == result
    }

    def "Should return false if token is valid"() {
        given: "Create token"
        def token = buildToken()
        def expect_result = false

        when: "Validate token"
        jumpInDaysForward(15)
        def result = tokenFacade.isTokenValid(token, getUsername())

        then: "Token is expired"
        expect_result == result
    }

    private String extractUsername(String token) {
        tokenFacade.extractLogin(token)
    }

    private String buildToken() {
        tokenFacade.buildToken(getUsername())
    }

    private static String getUsername() {
        final String username = "test-user"
        return username
    }
}
