package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.domain.api.UserError;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.passay.*;

import java.util.Arrays;
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
                        Case($(user -> Objects.isNull(user.getUsername())), NULL_USERNAME),
                        Case($(user -> Objects.isNull(user.getPassword())), NULL_PASSWORD),
                        Case($(user -> user.getUsername().isEmpty()), EMPTY_USERNAME),
                        Case($(user -> usernameIsNotDuplicated(user.getUsername())), DUPLICATED_USERNAME),
                        Case($(user -> validatePassword(user.getPassword())), PASSWORD_IS_TOO_WEAK)
                )
                .toEither(userRequest)
                .swap();
    }

    private boolean validatePassword(final String password) {
        PasswordValidator validator = buildPasswordValidationConditions();
        RuleResult result = validator.validate(new PasswordData(password));
        return !result.isValid();
    }

    private PasswordValidator buildPasswordValidationConditions() {
        return new PasswordValidator(Arrays.asList(
                new LengthRule(8, 15),
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                new CharacterRule(EnglishCharacterData.Digit, 2),
                new CharacterRule(EnglishCharacterData.Special, 2),
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                new WhitespaceRule())
        );
    }

    private Boolean findUserWithDuplicatedUsername(String username) {
        return userRepository.findAll()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    private Boolean usernameIsNotDuplicated(String username) {
        return !findUserWithDuplicatedUsername(username);
    }

}
