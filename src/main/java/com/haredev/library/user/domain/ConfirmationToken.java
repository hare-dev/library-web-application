package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.ConfirmationTokenResponse;
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
class ConfirmationToken {
    @Id
    private Long id;
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime confirmedAt;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private UserApplication userApplication;

    ConfirmationToken setConfirmedAt(String token) {
        return ConfirmationToken.builder()
                .id(this.id)
                .token(token)
                .createdAt(this.createdAt)
                .expiredAt(this.expiredAt)
                .confirmedAt(LocalDateTime.now())
                .build();
    }

    ConfirmationTokenResponse toConfirmationTokenResponse() {
        return ConfirmationTokenResponse.builder()
                .token(token)
                .build();
    }
}
