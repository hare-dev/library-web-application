package com.haredev.library.user.domain;

import org.springframework.data.repository.Repository;

interface ConfirmationTokenRepository extends Repository<ConfirmationToken, Long> {
    ConfirmationToken save(ConfirmationToken confirmationToken);
}
