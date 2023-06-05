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
    private final TokenManager tokenManager;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        var response = AuthenticationResponse.builder()
                .token(tokenManager.generateToken(request.getUsername()))
                .build();
        return ResponseEntity.ok(response);
    }
}
