package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.Authority;
import com.haredev.library.user.domain.api.UserError;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;

import static com.haredev.library.user.domain.api.UserError.*;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final int pageSize = 20;

    public Option<UserApplication> getUserByUsername(String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

    public Either<UserError, RegistrationResponse> registerUser(RegistrationRequest userRequest) {
        return validateParameters(userRequest)
                .map(this::createUser);
    }

    public Either<UserError, RegistrationResponse> registerAdmin(RegistrationRequest userRequest) {
        return validateParameters(userRequest)
                .map(this::createAdmin);
    }

    private Either<UserError, RegistrationRequest> validateParameters(RegistrationRequest userRequest) {
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
        return fetchAllUsersWithPageable(pageSize)
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    private boolean usernameIsDuplicated(String username) {
        return !findUserWithDuplicatedUsername(username);
    }

    public RegistrationResponse createUser(RegistrationRequest request) {
            UserApplication userApplication = UserApplication.newInstance(
                    request.getUserId(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    Authority.USER);
            return userRepository.save(userApplication).registrationResponse(); }

    public RegistrationResponse createAdmin(RegistrationRequest request) {
            UserApplication userApplication = UserApplication.newInstance(
                    request.getUserId(),
                    request.getUsername(),
                    passwordEncoder.encode(request.getPassword()),
                    Authority.ADMIN, Authority.USER);
            return userRepository.save(userApplication).registrationResponse();
    }

    public List<UserApplication> fetchAllUsersWithPageable(int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Option<UserApplication> findById(Long userId) {
        return Option.ofOptional(userRepository.findById(userId));
    }

    public void removeUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
