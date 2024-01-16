package com.haredev.library.security;

import com.haredev.library.security.dto.AuthenticationRequest;
import com.haredev.library.security.dto.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenFacade tokenFacade;

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var response = AuthenticationResponse.builder()
                .token(tokenFacade.buildToken(request.getUsername()))
                .build();
        return ResponseEntity.ok(response);
    }
}
