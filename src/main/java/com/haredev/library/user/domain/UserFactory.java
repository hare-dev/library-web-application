package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@RequiredArgsConstructor
class UserFactory {

    private final PasswordEncoder passwordEncoder;

    public final UserApplication buildUser(final RegistrationRequest request) {
        return UserApplication.builder()
                .id(request.userId())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .isActivated(false)
                .authorities(Set.of(Authority.USER))
                .build();
    }

    public final UserApplication buildAdmin(final RegistrationRequest request) {
        return UserApplication.builder()
                .id(request.userId())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .isActivated(false)
                .authorities(Set.of(Authority.USER, Authority.ADMIN))
                .build();
    }
}
