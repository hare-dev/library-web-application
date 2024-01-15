package com.haredev.library.user.controller.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class RegistrationResponse {
    private final Long userId;
    private final String username;
}
