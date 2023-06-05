package com.haredev.library.user.controller.validation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class UserRegistrationResponse {
    private final Long userId;
    private final String username;
}
