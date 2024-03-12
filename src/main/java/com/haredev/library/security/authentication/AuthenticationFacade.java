package com.haredev.library.security.authentication;

import com.haredev.library.security.authentication.controller.input.AuthenticationRequest;
import com.haredev.library.security.authentication.controller.output.AuthenticationResponse;
import com.haredev.library.security.authentication.errors.AuthenticationError;
import com.haredev.library.security.token.TokenFacade;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.error.UserError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.haredev.library.security.authentication.errors.AuthenticationError.WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD;
import static io.vavr.control.Either.left;
import static io.vavr.control.Either.right;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final TokenFacade tokenFacade;
    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    public Either<AuthenticationError, AuthenticationResponse> signIn(final AuthenticationRequest request) {
        return userFacade
                .findByUsername(request.username())
                .mapLeft(this::mapUserErrors)
                .flatMap(user -> authenticate(request.password(), user.password()));
    }

    private Either<AuthenticationError, AuthenticationResponse> authenticate(final String requestPassword,
                                                                             final String userPassword) {
        if (!passwordEncoder.matches(requestPassword, userPassword)) {
            return left(WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD);
        } else return right(generateToken(requestPassword));
    }

    private AuthenticationResponse generateToken(final String username) {
        return AuthenticationResponse.builder()
                .token(tokenFacade.buildToken(username))
                .build();
    }

    private AuthenticationError mapUserErrors(final UserError error) {
        return WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD;
    }
}