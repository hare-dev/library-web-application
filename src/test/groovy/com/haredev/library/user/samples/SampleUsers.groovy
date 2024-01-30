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

    static String nullUsername() {
        return null
    }

    static String nullPassword() {
        return null
    }

    static String emptyUsername() {
        return ""
    }

    static String emptyPassword() {
        return ""
    }

    static String lengthIsLessThanTwoCharacters() {
        return "abc"
    }

    static String lessThanTwoSpecialCharacters() {
        return "a12345678Z!"
    }

    static String lessThanOneUppercaseCharacters() {
        return "a12345678!@"
    }

    static String lessThanTwoDigitCharacters() {
        return "a1!@"
    }
}
