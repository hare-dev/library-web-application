package com.haredev.library.user.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends Repository<User, Long> {
    User save(User user);
    List<User> findAll();
    Optional<User> findByUsername(String username);
}
