package com.haredev.library.user.samples


import com.haredev.library.user.domain.dto.UserDetailsDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleUsers {
    static UserDetailsDto createUserSample(Long userId, String password, String username) {
        return UserDetailsDto.builder()
                .userId(userId)
                .password(password)
                .username(username)
                .build();
    }
}
