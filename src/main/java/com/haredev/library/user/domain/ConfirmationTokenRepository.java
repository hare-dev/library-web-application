package com.haredev.library.user.domain;

import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ConfirmationTokenRepository extends Repository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);
    VerificationToken save(VerificationToken verificationToken);
}
