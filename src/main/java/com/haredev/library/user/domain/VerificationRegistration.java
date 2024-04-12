package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.error.UserError;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;

import static com.haredev.library.user.domain.api.error.UserError.VERIFICATION_TOKEN_NOT_FOUND;

@RequiredArgsConstructor
class VerificationRegistration {
    private final UserManager userManager;
    private final VerificationMailCreator verificationMailCreator;
    private final VerificationTokenFactory verificationTokenFactory;
    private final VerificationTokenValidator verificationTokenValidator;
    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationToken createVerificationToken(final UserApplication userApplication) {
        final var token = verificationTokenFactory.buildToken(userApplication);
        return verificationTokenRepository.save(token);
    }

    public SimpleMailMessage createVerificationMail(final UserApplication userApplication, final String token) {
        return verificationMailCreator.createVerificationMail(userApplication.getUsername(), userApplication.getEmail(), token);
    }

    public Either<UserError, UserApplication> confirmVerificationToken(final String token) {
        return getVerificationToken(token)
                .toEither(VERIFICATION_TOKEN_NOT_FOUND)
                .flatMap(verificationTokenValidator::isConfirmedOrExpired)
                .peek(VerificationToken::confirmVerificationAt)
                .peek(verificationTokenRepository::save)
                .peek(user -> activateAccount(user.getUserApplication()))
                .map(VerificationToken::getUserApplication);
    }

    private Option<VerificationToken> getVerificationToken(final String token) {
        return Option.ofOptional(verificationTokenRepository.findByToken(token));
    }

    private void activateAccount(final UserApplication userApplication) {
        Option.of(userApplication)
                .peek(UserApplication::activateAccount)
                .peek(userManager::saveUser);
    }
}
