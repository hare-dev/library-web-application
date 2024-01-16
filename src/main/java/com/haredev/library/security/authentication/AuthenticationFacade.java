package com.haredev.library.security.authentication;

import com.haredev.library.security.TokenFacade;
import com.haredev.library.security.authentication.input.AuthenticationRequest;
import com.haredev.library.security.authentication.output.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationFacade {
    private final TokenFacade tokenFacade;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        return AuthenticationResponse.builder()
                .token(tokenFacade.buildToken(request.getUsername()))
                .build();
    }


}
