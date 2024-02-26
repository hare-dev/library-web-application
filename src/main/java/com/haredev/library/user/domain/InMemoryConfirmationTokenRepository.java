package com.haredev.library.user.domain;

import java.util.HashMap;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class InMemoryConfirmationTokenRepository implements ConfirmationTokenRepository {
    private final HashMap<Long, ConfirmationToken> inMemoryConfirmationToken = new HashMap<>();

    @Override
    public Optional<ConfirmationToken> findByToken(String token) {
        requireNonNull(token);
        return inMemoryConfirmationToken.values().stream().filter(value -> token.equals(value.getToken())).findAny();
    }

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        requireNonNull(confirmationToken);
        inMemoryConfirmationToken.put(confirmationToken.getId(), confirmationToken);
        return confirmationToken;
    }
}
