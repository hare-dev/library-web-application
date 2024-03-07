package com.haredev.library.security.authentication.controller.input;

public record AuthenticationRequest(
    String username,
    String password
) { }
