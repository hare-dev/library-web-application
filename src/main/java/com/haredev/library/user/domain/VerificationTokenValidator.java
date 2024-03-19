package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.error.UserError;
import io.vavr.control.Either;
import pl.amazingcode.timeflow.Time;

import static com.haredev.library.user.domain.api.error.UserError.VERIFICATION_TOKEN_IS_ALREADY_CONFIRMED;
import static com.haredev.library.user.domain.api.error.UserError.VERIFICATION_TOKEN_IS_EXPIRED;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

class VerificationTokenValidator {

    public final Either<UserError, VerificationToken> isConfirmedOrExpired(final VerificationToken verificationToken) {
        final var currentTime = Time.instance().now();
        if (verificationToken.getExpiredAt().isBefore(currentTime)) {
            return left(VERIFICATION_TOKEN_IS_EXPIRED);
        } else if (verificationToken.getConfirmedAt() != null) {
            return left(VERIFICATION_TOKEN_IS_ALREADY_CONFIRMED);
        }
        return right(verificationToken);
    }
}
