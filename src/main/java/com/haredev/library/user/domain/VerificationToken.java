package com.haredev.library.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class VerificationToken {
    @Id
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserApplication userApplication;

    VerificationToken setConfirmedAt(String token) {
        return VerificationToken.builder()
                .id(id)
                .token(token)
                .createdAt(createdAt)
                .expiredAt(expiredAt)
                .confirmedAt(LocalDateTime.now())
                .build();
    }
}
