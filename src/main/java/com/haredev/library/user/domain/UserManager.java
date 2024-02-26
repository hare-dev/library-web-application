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
import java.util.function.Function;

import static com.haredev.library.user.domain.api.UserError.*;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
class UserManager {
    private final UserRepository userRepository;
    private final UserFactory userFactory;
    private final ConfirmationTokenFactory confirmationTokenFactory;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final ConfirmationTokenValidation confirmationTokenValidation;
    private final ConfirmationTokenMapper confirmationTokenMapper;
    private final UserMapper userMapper;
    private static final int pageSize = 20;

    public UserApplication saveUser(UserApplication userApplication) {
        return userRepository.save(userApplication);
    }

    public Option<UserApplication> getUserByUsername(final String username) {
        return Option.ofOptional(userRepository.findByUsername(username));
    }

    public Either<UserError, ConfirmationTokenResponse> createConfirmationToken(final Long userId) {
        return findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .map(confirmationTokenFactory::buildToken)
                .map(confirmationTokenRepository::save)
                .map(confirmationTokenMapper::toConfirmationTokenResponse);
    }

    public Either<UserError, UserDetailsDto> confirmToken(final String token, final Long userId) {
        return getConfirmationToken(token)
                .toEither(CONFIRMATION_TOKEN_NOT_FOUND)
                .flatMap(confirmationTokenValidation::isConfirmed)
                .flatMap(confirmationTokenValidation::isExpired)
                .map(setConfirmationTime(token))
                .map(confirmationTokenRepository::save)
                .flatMap(UserApplication -> activateAccount(userId));
    }

    private Option<ConfirmationToken> getConfirmationToken(final String token) {
        return Option.ofOptional(confirmationTokenRepository.findByToken(token));
    }

    private static Function<ConfirmationToken, ConfirmationToken> setConfirmationTime(final String token) {
        return confirmed -> confirmed.setConfirmedAt(token);
    }

    private Either<UserError, UserDetailsDto> activateAccount(final Long userId) {
        return findUserById(userId).
                toEither(USER_NOT_FOUND)
                .map(UserApplication::activateAccount)
                .map(userRepository::save)
                .map(userMapper::toUserDetailsDto);
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
        userRepository.save(userApplication);
        return userMapper.toRegistrationResponse(userApplication);
    }

    public RegistrationResponse createAdmin(final RegistrationRequest request) {
        UserApplication userApplication = userFactory.buildAdmin(request);
        userRepository.save(userApplication);
        return userMapper.toRegistrationResponse(userApplication);
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
