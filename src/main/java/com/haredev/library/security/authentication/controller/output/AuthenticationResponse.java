package com.haredev.library.security.authentication.controller.output;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String token
) { }
