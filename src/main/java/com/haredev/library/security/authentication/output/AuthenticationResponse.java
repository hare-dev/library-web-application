package com.haredev.library.security.authentication.output;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
    String token
) { }
