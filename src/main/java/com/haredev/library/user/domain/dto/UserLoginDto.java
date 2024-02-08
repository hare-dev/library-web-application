package com.haredev.library.user.domain.dto;

import com.haredev.library.user.domain.api.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
@AllArgsConstructor
public class UserLoginDto {
    private final Long id;
    private final String username;
    private final String password;
    private final Set<Authority> authorities;
}
