package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.UserError;
import io.vavr.control.Either;
import pl.amazingcode.timeflow.Time;

import static com.haredev.library.user.domain.api.UserError.CONFIRMATION_TOKEN_IS_ALREADY_CONFIRMED;
import static com.haredev.library.user.domain.api.UserError.CONFIRMATION_TOKEN_IS_EXPIRED;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

class VerificationTokenValidator {

    public final Either<UserError, VerificationToken> isExpired(final VerificationToken verificationToken) {
        final var now = Time.instance().now();
        if (verificationToken.getExpiredAt().isBefore(now)) {
            return left(CONFIRMATION_TOKEN_IS_EXPIRED);
        }
        return right(verificationToken);
    }

    public final Either<UserError, VerificationToken> isConfirmed(final VerificationToken verificationToken) {
        if (verificationToken.getConfirmedAt() != null) {
            return left(CONFIRMATION_TOKEN_IS_ALREADY_CONFIRMED);
        }
        return right(verificationToken);
    }
}
