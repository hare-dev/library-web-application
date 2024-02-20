package com.haredev.library.security.authentication.input;

public record AuthenticationRequest(
    String username,
    String password
) { }
