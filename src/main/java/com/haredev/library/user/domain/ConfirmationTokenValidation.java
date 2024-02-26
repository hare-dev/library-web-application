package com.haredev.library.user.domain;

import com.haredev.library.user.domain.api.UserError;
import io.vavr.control.Either;

import java.time.LocalDateTime;

import static com.haredev.library.user.domain.api.UserError.CONFIRMATION_TOKEN_IS_ALREADY_CONFIRMED;
import static com.haredev.library.user.domain.api.UserError.CONFIRMATION_TOKEN_IS_EXPIRED;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

class ConfirmationTokenValidation {

    public final Either<UserError, ConfirmationToken> isExpired(final ConfirmationToken confirmationToken) {
        if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            return left(CONFIRMATION_TOKEN_IS_EXPIRED);
        }
        return right(confirmationToken);
    }

    public final Either<UserError, ConfirmationToken> isConfirmed(final ConfirmationToken confirmationToken) {
        if (confirmationToken.getConfirmedAt() != null) {
            return left(CONFIRMATION_TOKEN_IS_ALREADY_CONFIRMED);
        }
        return right(confirmationToken);
    }
}
