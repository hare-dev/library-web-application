package com.haredev.library.user.controller.output;

import lombok.Builder;

@Builder
public record VerificationTokenResponse(
    String token
) { }
