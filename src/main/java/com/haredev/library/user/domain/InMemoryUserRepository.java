package com.haredev.library.user.domain;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class InMemoryUserRepository implements UserRepository {
    private final HashMap<Long, UserApplication> inMemoryUser = new HashMap<>();

    @Override
    public UserApplication save(final UserApplication userApplication) {
        requireNonNull(userApplication);
        inMemoryUser.put(userApplication.toRegistrationResponse().userId(), userApplication);
        return userApplication;
    }

    @Override
    public List<UserApplication> findAll(final Pageable pageable) {
        return new ArrayList<>(inMemoryUser.values());
    }

    @Override
    public List<UserApplication> findAll() {
        return new ArrayList<>(inMemoryUser.values());
    }

    @Override
    public Optional<UserApplication> findByUsername(final String username) {
        requireNonNull(username);
        return inMemoryUser.values().stream().filter(user -> username.equals(user.getUsername())).findAny();
    }

    @Override
    public Optional<UserApplication> findById(final Long userId) {
        requireNonNull(userId);
        return Optional.ofNullable(inMemoryUser.get(userId));
    }

    @Override
    public void deleteById(final Long userId) {
        requireNonNull(userId);
        inMemoryUser.remove(userId);
    }
}
