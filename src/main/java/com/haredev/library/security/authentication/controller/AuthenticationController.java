package com.haredev.library.security.authentication.controller;

import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.security.authentication.AuthenticationFacade;
import com.haredev.library.security.authentication.controller.input.AuthenticationRequest;
import com.haredev.library.security.authentication.controller.output.AuthenticationResponse;
import com.haredev.library.security.authentication.errors.AuthenticationError;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AuthenticationController {
    private final AuthenticationFacade authenticateFacade;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        final Either<AuthenticationError, AuthenticationResponse> response = authenticateFacade.signIn(request);
        return ResponseResolver.resolve(response);
    }
}
