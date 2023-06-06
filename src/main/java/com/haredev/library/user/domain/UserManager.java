package com.haredev.library.user.domain;

import com.haredev.library.user.controller.validation.UserRegistrationRequest;
import com.haredev.library.user.controller.validation.UserRegistrationResponse;
import com.haredev.library.user.domain.api.Authority;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDto;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.haredev.library.user.domain.api.UserError.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.control.Either.left;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Either<UserError, UserApplication> getUserByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username)).toEither(UserError.USER_NOT_FOUND);
    }

    public Either<UserError, UserRegistrationResponse> registerUser(UserRegistrationRequest userRequest) {
        return validateParameters(userRequest)
                .flatMap(this::createUser);
    }

    public Either<UserError, UserRegistrationResponse> registerAdmin(UserRegistrationRequest userRequest) {
        return validateParameters(userRequest)
                .flatMap(this::createAdmin);
    }

    private Either<UserError, UserRegistrationRequest> validateParameters(UserRegistrationRequest userRequest) {
        return API.Match(userRequest)
                .option(
                        Case($(argument -> Objects.isNull(argument.getUsername())), USERNAME_IS_NULL),
                        Case($(argument -> Objects.isNull(argument.getPassword())), PASSWORD_IS_NULL),
                        Case($(argument -> argument.getUsername().isEmpty()), USERNAME_IS_EMPTY),
                        Case($(argument -> argument.getPassword().isEmpty()), PASSWORD_IS_EMPTY))
                .toEither(userRequest)
                .swap();
    }

    public Either<UserError, UserRegistrationResponse> createUser(UserRegistrationRequest request) {
        if (userWithUsernameNotExists(request.getUsername())) {
            UserApplication userApplication = UserApplication.newInstance(request.getUsername(), passwordEncoder.encode(request.getPassword()), Authority.USER);
            return Either.right(userRepository.save(userApplication).registrationResponse()); }
       return left(USERNAME_DUPLICATED);
    }

    public Either<UserError, UserRegistrationResponse> createAdmin(UserRegistrationRequest request) {
        if (userWithUsernameNotExists(request.getUsername())) {
            UserApplication userApplication = UserApplication.newInstance(request.getUsername(), passwordEncoder.encode(request.getPassword()), Authority.ADMIN, Authority.USER);
            return Either.right(userRepository.save(userApplication).registrationResponse()); }
        else return left(USERNAME_DUPLICATED);
    }

    private boolean userWithUsernameNotExists(String username) {
        return userRepository
                .findAll()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserApplication::toDto).collect(Collectors.toList());
    }
}
