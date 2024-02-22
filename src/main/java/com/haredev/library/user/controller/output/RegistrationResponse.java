package com.haredev.library.user.controller.output;

import lombok.Builder;

@Builder
public record RegistrationResponse(
    Long userId,
    String username,
    String email
) { }
