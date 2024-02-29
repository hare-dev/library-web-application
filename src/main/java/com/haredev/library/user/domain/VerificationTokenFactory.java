package com.haredev.library.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;

class VerificationTokenFactory {

    private static final Long expirationTimeInMinutes = 30L;

    public final VerificationToken buildToken(final UserApplication userApplication) {
        return VerificationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(expirationTimeInMinutes))
                .userApplication(userApplication)
                .build();
    }
}
