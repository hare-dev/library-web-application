package com.haredev.library.user.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryUserRepository implements UserRepository {
    ConcurrentHashMap<Long, User> inMemory = new ConcurrentHashMap<>();

    @Override
    public User save(User user) {
        requireNonNull(user);
        inMemory.put(user.registrationResponse().getUserId(), user);
        return user;
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        requireNonNull(username);
        return inMemory.values().stream().filter(user -> username.equals(user.getUsername())).findAny();
    }
}
