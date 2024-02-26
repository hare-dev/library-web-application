package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.ConfirmationTokenResponse;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserLoginDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UserFacade {
    private final UserManager userManager;

    public Either<UserError, RegistrationResponse> registerAsUser(final RegistrationRequest userRequest) {
        return userManager.registerUser(userRequest);
    }

    public Either<UserError, RegistrationResponse> registerAsAdmin(final RegistrationRequest userRequest) {
        return userManager.registerAdmin(userRequest);
    }

    public Either<UserError, UserLoginDto> findByUsername(final String username) {
        return userManager.getUserByUsername(username)
                .map(UserApplication::toLoginDto)
                .toEither(UserError.USER_NOT_FOUND);
    }

    public Either<UserError, UserDetailsDto> findUserById(final Long userId) {
        return userManager.findUserById(userId)
                .map(UserApplication::toUserDetailsDto)
                .toEither(UserError.USER_NOT_FOUND);
    }

    public List<UserDetailsDto> fetchAllUsers(final int page) {
        return userManager.fetchAllUsersWithPageable(page)
                .stream()
                .map(UserApplication::toUserDetailsDto)
                .collect(Collectors.toList());
    }

    public Either<UserError, UserDetailsDto> promoteToAdmin(final Long userId) {
        return userManager.promoteToAdmin(userId);
    }

    public Either<UserError, UserDetailsDto> changeUsername(final Long userId, final String username) {
        return userManager.changeUsername(userId, username);
    }

    public Either<UserError, ConfirmationTokenResponse> createConfirmationToken(final Long userId) {
        return userManager.createConfirmationToken(userId);
    }

    public Either<UserError, UserDetailsDto> confirmRegistration(final String token, final Long userId) {
        return userManager.confirmToken(token, userId);
    }

    public void removeUserById(final Long userId) {
        userManager.removeUserById(userId);
    }
}
