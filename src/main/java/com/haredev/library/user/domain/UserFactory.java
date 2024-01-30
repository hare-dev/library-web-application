package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public final UserApplication buildUser(RegistrationRequest request) {
        return UserApplication.newInstance(
                request.getUserId(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Authority.USER);
    }

    public final UserApplication buildAdmin(RegistrationRequest request) {
        return UserApplication.newInstance(
                request.getUserId(),
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                Authority.ADMIN, Authority.USER);
    }
}
