package com.haredev.library.security.samples

import com.haredev.library.security.authentication.controller.input.AuthenticationRequest

final class LoginCredentialsSample {
    static AuthenticationRequest createCorrectCredentialsSample(final String username, final String password) {
        return AuthenticationRequest.builder()
                .username(username)
                .password(password)
                .build()
    }

    static AuthenticationRequest createIncorrectUsernameSample(final String password) {
        return AuthenticationRequest.builder()
                .username("INCORRECT_USERNAME")
                .password(password)
                .build()
    }

    static AuthenticationRequest createIncorrectPasswordSample(final String username) {
        return AuthenticationRequest.builder()
                .username(username)
                .password("INCORRECT_PASSWORD")
                .build()
    }
}
