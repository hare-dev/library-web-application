package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.ConfirmationTokenResponse;
import com.haredev.library.user.domain.api.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import static com.haredev.library.user.domain.api.UserError.CONFIRMATION_TOKEN_NOT_FOUND;
import static com.haredev.library.user.domain.api.UserError.USER_NOT_FOUND;

@RequiredArgsConstructor
class UserRegistrationConfirmation {

    private final UserManager userManager;
    private final UserMapper userMapper;
    private final VerificationTokenFactory verificationTokenFactory;
    private final VerificationTokenRepository verificationTokenRepository;
    private final ConfirmationTokenValidation confirmationTokenValidation;
    private final VerificationTokenMapper verificationTokenMapper;

    public Either<UserError, ConfirmationTokenResponse> createConfirmationToken(final Long userId) {
        return userManager.findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .map(verificationTokenFactory::buildToken)
                .map(verificationTokenRepository::save)
                .map(verificationTokenMapper::toConfirmationTokenResponse);
    }

    public Either<UserError, UserDetailsDto> confirmToken(final String token, final Long userId) {
        return getConfirmationToken(token)
                .toEither(CONFIRMATION_TOKEN_NOT_FOUND)
                .flatMap(confirmationTokenValidation::isConfirmed)
                .flatMap(confirmationTokenValidation::isExpired)
                .map(setConfirmationTime(token))
                .map(verificationTokenRepository::save)
                .flatMap(UserApplication -> activateAccount(userId));
    }

    private Option<VerificationToken> getConfirmationToken(final String token) {
        return Option.ofOptional(verificationTokenRepository.findByToken(token));
    }

    private static Function<VerificationToken, VerificationToken> setConfirmationTime(final String token) {
        return confirmed -> confirmed.setConfirmedAt(token);
    }

    private Either<UserError, UserDetailsDto> activateAccount(final Long userId) {
        return userManager.findUserById(userId).
                toEither(USER_NOT_FOUND)
                .map(UserApplication::activateAccount)
                .map(userManager::saveUser)
                .map(userMapper::toUserDetailsDto);
    }
}
