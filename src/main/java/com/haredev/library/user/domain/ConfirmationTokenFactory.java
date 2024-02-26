package com.haredev.library.user.domain;

import java.time.LocalDateTime;
import java.util.UUID;

class ConfirmationTokenFactory {
    private static final Long expirationTimeInMinutes = 30L;

    public final ConfirmationToken buildToken(final UserApplication userApplication) {
        return ConfirmationToken.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(expirationTimeInMinutes))
                .userApplication(userApplication)
                .build();
    }
}
