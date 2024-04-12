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
    private final UserMapper userMapper;
    private final UserManager userManager;
    private final UserFactory userFactory;
    private final VerificationRegistration verificationRegistration;
    private final VerificationMailSenderClient VerificationMailSenderClient;

    public Either<UserError, RegistrationResponse> registerUser(final RegistrationRequest userRequest) {
        if (validateUsernameDuplication(userRequest.username())) {
            final var user = createUser(userRequest);
            final var token = verificationRegistration.createVerificationToken(user).getToken();
            final var verificationMail = verificationRegistration.createVerificationMail(user, token);
            VerificationMailSenderClient.send(verificationMail);
            return right(userMapper.toRegistrationResponse(user, token));
        }
        return left(DUPLICATED_USERNAME);
    }

    private Boolean validateUsernameDuplication(final String username) {
        return userManager.fetchAllUsers()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    private UserApplication createUser(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildUser(request);
        return userManager.saveUser(userApplication);
    }
}
