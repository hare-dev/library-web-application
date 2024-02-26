package com.haredev.library.user.domain;

import java.util.HashMap;

import static java.util.Objects.requireNonNull;

class InMemoryConfirmationTokenRepository implements ConfirmationTokenRepository {
    private final HashMap<Long, ConfirmationToken> inMemoryConfirmationToken = new HashMap<>();

    @Override
    public ConfirmationToken save(ConfirmationToken confirmationToken) {
        requireNonNull(confirmationToken);
        inMemoryConfirmationToken.put(confirmationToken.getId(), confirmationToken);
        return confirmationToken;
    }
}
