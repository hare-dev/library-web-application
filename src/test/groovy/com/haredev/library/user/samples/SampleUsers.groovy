package com.haredev.library.user.samples

import com.haredev.library.user.controller.input.RegistrationRequest
import groovy.transform.CompileStatic

@CompileStatic
final class SampleUsers {
    static RegistrationRequest createUserSample(Long userId, String username, String password) {
        return RegistrationRequest.builder()
                .userId(userId)
                .username(username)
                .password(password)
                .build();
    }
}
