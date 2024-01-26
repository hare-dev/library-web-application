package com.haredev.library.user.domain;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryUserRepository implements UserRepository {
    private final ConcurrentHashMap<Long, UserApplication> inMemoryUser = new ConcurrentHashMap<>();

    @Override
    public UserApplication save(UserApplication userApplication) {
        requireNonNull(userApplication);
        inMemoryUser.put(userApplication.toRegistrationResponse().getUserId(), userApplication);
        return userApplication;
    }

    @Override
    public List<UserApplication> findAll(Pageable pageable) {
        return new ArrayList<>(inMemoryUser.values());
    }

    @Override
    public List<UserApplication> findAll() {
        return new ArrayList<>(inMemoryUser.values());
    }

    @Override
    public Optional<UserApplication> findByUsername(String username) {
        requireNonNull(username);
        return inMemoryUser.values().stream().filter(user -> username.equals(user.getUsername())).findAny();
    }

    @Override
    public Optional<UserApplication> findById(Long userId) {
        requireNonNull(userId);
        return Optional.ofNullable(inMemoryUser.get(userId));
    }

    @Override
    public void deleteById(Long userId) {
        requireNonNull(userId);
        inMemoryUser.remove(userId);
    }
}
