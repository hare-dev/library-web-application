package com.haredev.library.user.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class LoginCredentials {
    private final String username;
    private final String password;
}
