package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.AccountStatus;
import com.haredev.library.user.domain.api.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public final UserApplication buildUser(final RegistrationRequest request) {
        return UserApplication.builder()
                .id(request.id())
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .accountStatus(AccountStatus.NOT_ACTIVATED)
                .authorities(userAuthorities())
                .build();
    }

    private Set<Authority> userAuthorities() {
        return new HashSet<>(List.of(Authority.USER));
    }
}
