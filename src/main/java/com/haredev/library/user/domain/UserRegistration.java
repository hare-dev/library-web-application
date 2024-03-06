package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.error.UserError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import static com.haredev.library.user.domain.api.error.UserError.DUPLICATED_USERNAME;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
class UserRegistration {

    private final UserFactory userFactory;
    private final UserManager userManager;
    private final UserMapper userMapper;

    public Either<UserError, RegistrationResponse> registerUser(final RegistrationRequest userRequest) {
        if (userWithDuplicatedUsernameNotExist(userRequest.username())) {
            return right(createUser(userRequest));
        }
        return left(DUPLICATED_USERNAME);
    }

    public Either<UserError, RegistrationResponse> registerAdmin(final RegistrationRequest userRequest) {
        if (userWithDuplicatedUsernameNotExist(userRequest.username())) {
            return right(createAdmin(userRequest));
        }
        return left(DUPLICATED_USERNAME);
    }

    private Boolean userWithDuplicatedUsernameNotExist(final String username) {
        return userManager.fetchAllUsers()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    private RegistrationResponse createUser(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildUser(request);
        userManager.saveUser(userApplication);
        return userMapper.toRegistrationResponse(userApplication);
    }

    private RegistrationResponse createAdmin(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildAdmin(request);
        userManager.saveUser(userApplication);
        return userMapper.toRegistrationResponse(userApplication);
    }
}
