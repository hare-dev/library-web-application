package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserLoginDto;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserFacade {
    private final UserManager userManager;

    public Either<UserError, RegistrationResponse> registerAsUser(RegistrationRequest userRequest) {
        return userManager.registerUser(userRequest);
    }

    public Either<UserError, RegistrationResponse> registerAsAdmin(RegistrationRequest userRequest) {
        return userManager.registerAdmin(userRequest);
    }

    public Either<UserError, UserLoginDto> findByUsername(String username) {
        return userManager.getUserByUsername(username)
                .map(UserApplication::toLoginDto)
                .toEither(UserError.USER_NOT_FOUND);
    }

    public Either<UserError, UserDetailsDto> findUserById(Long userId) {
        return userManager.findById(userId)
                .map(UserApplication::toUserDetails)
                .toEither(UserError.USER_NOT_FOUND);
    }

    public List<UserDetailsDto> fetchAllUsers(int page) {
        return userManager.fetchAllUsersWithPageable(page)
                .stream()
                .map(UserApplication::toUserDetails)
                .collect(Collectors.toList());
    }

    public void removeUserById(Long userId) {
        userManager.removeUserById(userId);
    }
}
