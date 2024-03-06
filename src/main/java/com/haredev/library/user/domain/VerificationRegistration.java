package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.VerificationTokenResponse;
import com.haredev.library.user.domain.api.error.UserError;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

import static com.haredev.library.user.domain.api.error.UserError.VERIFICATION_TOKEN_NOT_FOUND;
import static com.haredev.library.user.domain.api.error.UserError.USER_NOT_FOUND;

@RequiredArgsConstructor
class VerificationRegistration {

    private final UserManager userManager;
    private final UserMapper userMapper;
    private final VerificationTokenFactory verificationTokenFactory;
    private final VerificationTokenRepository verificationTokenRepository;
    private final VerificationTokenValidator verificationTokenValidator;
    private final VerificationTokenMapper verificationTokenMapper;

    public Either<UserError, VerificationTokenResponse> createVerificationToken(final Long userId) {
        return userManager.findUserById(userId)
                .toEither(USER_NOT_FOUND)
                .map(verificationTokenFactory::buildToken)
                .map(verificationTokenRepository::save)
                .map(verificationTokenMapper::verificationTokenResponse);
    }

    public Either<UserError, UserDetailsDto> confirmVerificationToken(final String token, final Long userId) {
        return getVerificationToken(token)
                .toEither(VERIFICATION_TOKEN_NOT_FOUND)
                .flatMap(verificationTokenValidator::isConfirmed)
                .flatMap(verificationTokenValidator::isExpired)
                .map(setVerificationTime(token))
                .map(verificationTokenRepository::save)
                .flatMap(UserApplication -> activateAccount(userId));
    }

    private Option<VerificationToken> getVerificationToken(final String token) {
        return Option.ofOptional(verificationTokenRepository.findByToken(token));
    }

    private static Function<VerificationToken, VerificationToken> setVerificationTime(final String token) {
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
