package com.haredev.library.user.controller.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegistrationRequest {
    private final Long userId;
    private final String username;
    private final String password;
}
