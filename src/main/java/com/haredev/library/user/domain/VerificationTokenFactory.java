package com.haredev.library.user.domain;

import pl.amazingcode.timeflow.Time;

import java.time.temporal.ChronoUnit;
import java.util.UUID;

class VerificationTokenFactory {

    public final VerificationToken buildToken(final UserApplication userApplication) {
        var expiredAt = Time.instance().now().plus(1, ChronoUnit.DAYS);
        var now = Time.instance().now();
        var token = UUID.randomUUID().toString();
        return VerificationToken.builder()
                .token(token)
                .createdAt(now)
                .expiredAt(expiredAt)
                .userApplication(userApplication)
                .build();
    }
}
