package com.haredev.library.user.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface UserRepository extends Repository<UserApplication, Long> {
    UserApplication save(UserApplication userApplication);
    List<UserApplication> findAll(Pageable pageable);
    Optional<UserApplication> findByUsername(String username);
    Optional<UserApplication> findById(Long userId);
    void deleteById(Long userId);
}
