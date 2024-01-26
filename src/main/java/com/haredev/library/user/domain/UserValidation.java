package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.UserError;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.haredev.library.user.domain.api.UserError.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RequiredArgsConstructor
class UserValidation {
    private final UserRepository userRepository;

    public Either<UserError, RegistrationRequest> validateParameters(RegistrationRequest userRequest) {
        return API.Match(userRequest)
                .option(
                        Case($(username -> Objects.isNull(username.getUsername())), USERNAME_IS_NULL),
                        Case($(password -> Objects.isNull(password.getPassword())), PASSWORD_IS_NULL),
                        Case($(username -> username.getUsername().isEmpty()), USERNAME_IS_EMPTY),
                        Case($(password -> password.getPassword().isEmpty()), PASSWORD_IS_EMPTY),
                        Case($(username -> usernameIsDuplicated(username.getUsername())), USERNAME_DUPLICATED))
                .toEither(userRequest)
                .swap();
    }

    private boolean findUserWithDuplicatedUsername(String username) {
        return userRepository.findAll()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    private boolean usernameIsDuplicated(String username) {
        return !findUserWithDuplicatedUsername(username);
    }
}
