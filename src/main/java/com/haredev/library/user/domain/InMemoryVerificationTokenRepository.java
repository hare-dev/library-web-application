package com.haredev.library.user.domain;

import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class InMemoryVerificationTokenRepository implements VerificationTokenRepository {

    private final HashMap<Long, VerificationToken> inMemoryConfirmationToken = new HashMap<>();

    @Override
    public Optional<VerificationToken> findByToken(String token) {
        requireNonNull(token);
        return inMemoryConfirmationToken.values().stream().filter(value -> token.equals(value.getToken())).findAny();
    }

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        requireNonNull(verificationToken);
        inMemoryConfirmationToken.put(verificationToken.getId(), verificationToken);
        return verificationToken;
    }
}
