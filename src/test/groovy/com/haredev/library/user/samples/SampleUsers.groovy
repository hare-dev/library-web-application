package com.haredev.library.user.samples

import com.haredev.library.user.controller.input.RegistrationRequest
import groovy.transform.CompileStatic

@CompileStatic
trait SampleUsers {
    final RegistrationRequest user = createUserSample(0L, "user", "a12345678Z!@", "user_example@gmail.com")
    final Long user_with_this_id_not_exist = 5000L
    final String verification_token_with_this_id_not_exist = 12345

    private RegistrationRequest createUserSample(final Long id, final String username, final String password, final String email) {
        return RegistrationRequest.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}