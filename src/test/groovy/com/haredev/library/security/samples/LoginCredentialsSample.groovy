package com.haredev.library.security.samples

import com.haredev.library.security.authentication.controller.input.AuthenticationRequest

class LoginCredentialsSample {
    static AuthenticationRequest createCorrectCredentialsSample(String username, String password) {
        return AuthenticationRequest.builder()
                .username(username)
                .password(password)
                .build()
    }

    static AuthenticationRequest createIncorrectUsernameSample(String password) {
        return AuthenticationRequest.builder()
                .username("INCORRECT_USERNAME")
                .password(password)
                .build()
    }

    static AuthenticationRequest createIncorrectPasswordSample(String username) {
        return AuthenticationRequest.builder()
                .username(username)
                .password("INCORRECT_PASSWORD")
                .build()
    }
}
