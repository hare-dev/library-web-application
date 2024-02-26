package com.haredev.library.user.controller.output;

import lombok.Builder;

@Builder
public record ConfirmationTokenResponse(
    String token
) { }
