package com.haredev.library.security.authentication.samples

import com.haredev.library.security.authentication.controller.input.AuthenticationRequest
import com.haredev.library.user.controller.input.RegistrationRequest
import groovy.transform.CompileStatic

@CompileStatic
trait AuthenticationCredentialsSample {
    final RegistrationRequest user = createUserSample(0L, "user", "a12345678Z!@", "user_example@gmail.com")
    AuthenticationRequest authentication_correct_credentials = createCorrectCredentialsSample(user.username(), user.password())
    AuthenticationRequest authentication_with_incorrect_password = createIncorrectPasswordSample(user.username())
    AuthenticationRequest authentication_with_incorrect_username = createIncorrectUsernameSample(user.password())

    private RegistrationRequest createUserSample(final Long id, final String username, final String password, final String email) {
        return RegistrationRequest.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }

    private AuthenticationRequest createCorrectCredentialsSample(final String username, final String password) {
        return AuthenticationRequest.builder()
                .username(username)
                .password(password)
                .build()
    }

    private AuthenticationRequest createIncorrectUsernameSample(final String password) {
        return AuthenticationRequest.builder()
                .username("INCORRECT_USERNAME")
                .password(password)
                .build()
    }

    private AuthenticationRequest createIncorrectPasswordSample(final String username) {
        return AuthenticationRequest.builder()
                .username(username)
                .password("INCORRECT_PASSWORD")
                .build()
    }
}