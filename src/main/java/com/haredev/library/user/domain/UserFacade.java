package com.haredev.library.user.domain;

import com.haredev.library.user.controller.validation.RegistrationRequest;
import com.haredev.library.user.controller.validation.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserFacade {
    private final UserManager userManager;

    public Either<UserError, RegistrationResponse> registerAsUser(RegistrationRequest userRequest) {
        return userManager.registerUser(userRequest);
    }

    public Either<UserError, RegistrationResponse> registerAsAdmin(RegistrationRequest userRequest) {
        return userManager.registerAdmin(userRequest);
    }

    public Either<UserError, UserDetailsDto> findByUsername(String username) {
        return userManager.getUserByUsername(username)
                .map(UserApplication::userDetails)
                .toEither(UserError.USER_NOT_FOUND);
    }

    public List<UserDto> fetchAllUsers() {
        return userManager.findAll();
    }
}
