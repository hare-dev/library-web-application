package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.error.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import static com.haredev.library.user.domain.api.Authority.ADMIN;
import static com.haredev.library.user.domain.api.error.UserError.USER_IS_ALREADY_ADMIN;
import static com.haredev.library.user.domain.api.error.UserError.USER_NOT_FOUND;
import static io.vavr.control.Either.left;

@RequiredArgsConstructor
class UserPromoter {
    private final UserManager userManager;
    private final UserMapper userMapper;

    public Either<UserError, UserDetailsDto> promoteToAdmin(final Long userId) {
        return userManager.findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .flatMap(this::canPromoteToAdmin)
                .map(user -> {
                    user.promoteToAdmin();
                    return userMapper.toUserDetailsDto(userManager.saveUser(user));
                });
    }

    private Either<UserError, UserApplication> canPromoteToAdmin(final UserApplication userApplication) {
        if (userApplication.getAuthorities().contains(ADMIN)) {
            return left(USER_IS_ALREADY_ADMIN);
        }
        return Either.right(userApplication);
    }
}
