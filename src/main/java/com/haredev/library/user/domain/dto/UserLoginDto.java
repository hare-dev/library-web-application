package com.haredev.library.user.domain.dto;

import com.haredev.library.user.domain.api.Authority;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserLoginDto(
    Long id,
    String username,
    String password,
    Set<Authority> authorities
) { }
