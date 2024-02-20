package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public final UserApplication buildUser(final RegistrationRequest request) {
        return UserApplication.newInstance(
                request.userId(),
                request.username(),
                passwordEncoder.encode(request.password()),
                Authority.USER);
    }

    public final UserApplication buildAdmin(final RegistrationRequest request) {
        return UserApplication.newInstance(
                request.userId(),
                request.username(),
                passwordEncoder.encode(request.password()),
                Authority.ADMIN, Authority.USER);
    }
}
