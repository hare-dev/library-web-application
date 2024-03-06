package com.haredev.library.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.amazingcode.timeflow.Time;

import java.time.Instant;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VerificationToken {
    @Id
    private Long id;
    private String token;
    private Instant createdAt;
    private Instant expiredAt;
    private Instant confirmedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserApplication userApplication;

    VerificationToken setConfirmedAt(String token) {
        final var now = Time.instance().now();
        return VerificationToken.builder()
                .id(id)
                .token(token)
                .createdAt(createdAt)
                .expiredAt(expiredAt)
                .confirmedAt(now)
                .build();
    }
}
