package com.haredev.library.security.authentication.controller.input;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
    String username,
    String password
) { }
