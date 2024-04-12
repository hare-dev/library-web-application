package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.error.UserError;
import com.haredev.library.user.domain.dto.UserLoginDto;
import com.haredev.library.user.domain.dto.UserPublicDetailsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.haredev.library.user.domain.api.error.UserError.USER_NOT_FOUND;

@RequiredArgsConstructor
public class UserFacade {
    private final UserMapper userMapper;
    private final UserManager userManager;
    private final UserUpdater userUpdater;
    private final UserPromoter userPromoter;
    private final UserRegistration userRegistration;
    private final VerificationRegistration verificationRegistration;

    public Either<UserError, RegistrationResponse> registerAsUser(final RegistrationRequest userRequest) {
        return userRegistration.registerUser(userRequest);
    }

    public Either<UserError, UserPublicDetailsDto> confirmRegistration(final String token) {
        return verificationRegistration.confirmVerificationToken(token)
                .map(userMapper::toUserDetailsDto);
    }

    public Either<UserError, UserLoginDto> findByUsername(final String username) {
        return userManager.getUserByUsername(username)
                .map(userMapper::toLoginDto)
                .toEither(USER_NOT_FOUND);
    }

    public Either<UserError, UserPublicDetailsDto> findUserById(final Long userId) {
        return userManager.findUserById(userId)
                .map(userMapper::toUserDetailsDto)
                .toEither(USER_NOT_FOUND);
    }

    public List<UserPublicDetailsDto> fetchAllUsers(final int page) {
        return userManager.fetchAllUsersWithPageable(page)
                .stream()
                .map(userMapper::toUserDetailsDto)
                .collect(Collectors.toList());
    }

    public Either<UserError, UserPublicDetailsDto> promoteToAdmin(final Long userId) {
        return userPromoter.promoteToAdmin(userId);
    }

    public Either<UserError, UserPublicDetailsDto> changeUsername(final Long userId, final String newUsername) {
        return userUpdater.changeUsername(userId, newUsername);
    }

    public void removeUserById(final Long userId) {
        userManager.removeUserById(userId);
    }
}
