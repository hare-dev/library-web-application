package com.haredev.library.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryUserRepository implements UserRepository {
    ConcurrentHashMap<Long, UserApplication> inMemory = new ConcurrentHashMap<>();

    @Override
    public UserApplication save(UserApplication userApplication) {
        requireNonNull(userApplication);
        inMemory.put(userApplication.registrationResponse().getUserId(), userApplication);
        return userApplication;
    }

    @Override
    public List<UserApplication> findAll() {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public Optional<UserApplication> findByUsername(String username) {
        requireNonNull(username);
        return inMemory.values().stream().filter(user -> username.equals(user.getUsername())).findAny();
    }
}
