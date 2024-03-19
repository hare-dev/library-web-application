package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.error.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import static com.haredev.library.user.domain.api.error.UserError.USER_NOT_FOUND;

@RequiredArgsConstructor
class UserUpdater {

    private final UserManager userManager;
    private final UserMapper userMapper;

    public Either<UserError, UserDetailsDto> changeUsername(final Long userId, final String newUsername) {
        return userManager.findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .peek(user -> user.changeUsername(newUsername))
                .map(userManager::saveUser)
                .map(userMapper::toUserDetailsDto);
    }
}
