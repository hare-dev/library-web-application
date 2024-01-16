package com.haredev.library.security.authentication;

import com.haredev.library.security.authentication.input.AuthenticationRequest;
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
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authenticateFacade.signIn(request));
    }
}
