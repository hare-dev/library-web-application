package com.haredev.library.user.domain.dto;

import com.haredev.library.user.domain.api.Authority;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserDetailsDto(
        Long id,
        String username,
        Set<Authority> authorities,
        Boolean isActivated
) { }