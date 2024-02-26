package com.haredev.library.user.domain;

import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RequiredArgsConstructor
class UserManager {

    private final UserRepository userRepository;
    private static final int pageSize = 20;

    public UserApplication saveUser(UserApplication userApplication) {
        return userRepository.save(userApplication);
    }

    public Option<UserApplication> getUserByUsername(final String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

    public List<UserApplication> fetchAllUsersWithPageable(final int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public List<UserApplication> fetchAllUsers() {
        return userRepository.findAll();
    }

    public Option<UserApplication> findUserById(final Long userId) {
        return Option.ofOptional(userRepository.findById(userId));
    }

    public void removeUserById(final Long userId) {
        userRepository.deleteById(userId);
    }
}
