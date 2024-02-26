package com.haredev.library.user.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Long> {
    Optional<ConfirmationToken> findByToken(String token);
    ConfirmationToken save(ConfirmationToken confirmationToken);
}
