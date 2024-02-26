package com.haredev.library.user.domain;

import com.haredev.library.user.controller.input.RegistrationRequest;
import com.haredev.library.user.controller.output.ConfirmationTokenResponse;
import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.haredev.library.user.domain.api.Authority.ADMIN;
import static com.haredev.library.user.domain.api.UserError.*;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ConfirmationTokenFactory confirmationTokenFactory;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private static final int pageSize = 20;

    public Option<UserApplication> getUserByUsername(final String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

    public Either<UserError, ConfirmationTokenResponse> createConfirmationToken(final Long userId) {
        return findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .map(confirmationTokenFactory::buildToken)
                .map(token -> confirmationTokenRepository.save(token).toConfirmationTokenResponse());
    }

    public Either<UserError, UserDetailsDto> promoteToAdmin(final Long userId) {
        return findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .flatMap(this::canPromoteToAdmin)
                .map(user -> {
                    user.promoteToAdmin();
                    return userRepository.save(user).toUserDetails();
                });
    }

    private Either<UserError, UserApplication> canPromoteToAdmin(final UserApplication userApplication) {
        if (userApplication.getAuthorities().contains(ADMIN)) {
            return left(USER_IS_ALREADY_ADMIN);
        }
        return Either.right(userApplication);
    }

    public Either<UserError, UserDetailsDto> changeUsername(final Long userId, final String username) {
        return findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .map(user -> user.changeUsername(username))
                .map(userRepository::save)
                .map(UserApplication::toUserDetails);
    }

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
        return userRepository.findAll()
                .stream()
                .noneMatch(user -> username.equals(user.getUsername()));
    }

    public RegistrationResponse createUser(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildUser(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public RegistrationResponse createAdmin(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildAdmin(request);
        return userRepository.save(userApplication).toRegistrationResponse();
    }

    public List<UserApplication> fetchAllUsersWithPageable(final int page) {
        return userRepository.findAll(PageRequest.of(page, pageSize));
    }

    public Option<UserApplication> findUserById(final Long userId) {
        return Option.ofOptional(userRepository.findById(userId));
    }

    public void removeUserById(final Long userId) {
        userRepository.deleteById(userId);
    }
}
