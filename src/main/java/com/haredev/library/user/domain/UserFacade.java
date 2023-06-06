package com.haredev.library.user.domain;

import com.haredev.library.user.controller.validation.UserRegistrationRequest;
import com.haredev.library.user.controller.validation.UserRegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserFacade {
    private final UserManager userManager;

    public Either<UserError, UserRegistrationResponse> registerAsUser(UserRegistrationRequest userRequest) {
        return userManager.registerUser(userRequest);
    }

    public Either<UserError, UserRegistrationResponse> registerAsAdmin(UserRegistrationRequest userRequest) {
        return userManager.registerAdmin(userRequest);
    }

    public Either<UserError, UserDetailsDto> findByUsername(String username) {
        return userManager.getUserByUsername(username).map(UserApplication::userDetails);
    }

    public List<UserDto> fetchAllUsers() {
        return userManager.findAll();
    }
}
