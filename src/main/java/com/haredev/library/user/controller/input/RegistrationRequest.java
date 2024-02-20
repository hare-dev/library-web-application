package com.haredev.library.user.controller.input;

import lombok.Builder;

@Builder
public record RegistrationRequest(
    Long userId,
    String username,
    String password
) { }
