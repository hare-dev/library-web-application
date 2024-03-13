package com.haredev.library.security.authentication;

import com.haredev.library.security.authentication.controller.input.AuthenticationRequest;
import com.haredev.library.security.authentication.controller.output.AuthenticationResponse;
import com.haredev.library.security.authentication.errors.AuthenticationError;
import com.haredev.library.security.token.TokenFacade;
import com.haredev.library.user.domain.UserFacade;
import com.haredev.library.user.domain.api.error.UserError;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.haredev.library.security.authentication.errors.AuthenticationError.WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD;

@RequiredArgsConstructor
public class AuthenticationFacade {
    private final TokenFacade tokenFacade;
    private final UserFacade userFacade;
    private final PasswordEncoder passwordEncoder;

    public Either<AuthenticationError, AuthenticationResponse> signIn(final AuthenticationRequest authenticationRequest) {
        return userFacade
                .findByUsername(authenticationRequest.username())
                .mapLeft(this::mapError)
                .flatMap(user -> checkCandidate(authenticationRequest, user.password()))
                .map(request -> generateToken(request.username()));
    }

    private Either<AuthenticationError, AuthenticationRequest> checkCandidate(final AuthenticationRequest authenticationRequest,
                                                                              final String userPassword) {
        return Option.of(authenticationRequest)
                .filter(request -> passwordEncoder.matches(request.password(), userPassword))
                .toEither(WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD);
    }

    private AuthenticationResponse generateToken(final String username) {
        return AuthenticationResponse.builder()
                .token(tokenFacade.buildToken(username))
                .build();
    }

    private AuthenticationError mapError(final UserError error) {
        return WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD;
    }
}