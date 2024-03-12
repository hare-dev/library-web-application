package com.haredev.library.user.samples

import com.haredev.library.user.controller.input.RegistrationRequest
import groovy.transform.CompileStatic

@CompileStatic
final class SampleUsers {
    static final Long notExistUserWithThisId = 5000L;
    static final String notExistToken = 12345;

    static RegistrationRequest createUserSample(final Long userId, final String username, final String password, final String email) {
        return RegistrationRequest.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .email(email)
                .build();
    }
}
