package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.dto.UserDetailsDto;
import com.haredev.library.user.domain.dto.UserLoginDto;

class UserMapper {
    RegistrationResponse toRegistrationResponse(final UserApplication userApplication) {
        return RegistrationResponse.builder()
                .id(userApplication.getId())
                .username(userApplication.getUsername())
                .email(userApplication.getEmail())
                .activationStatus(userApplication.getActivationStatus())
                .build();
    }

    UserDetailsDto toUserDetailsDto(final UserApplication userApplication) {
        return UserDetailsDto.builder()
                .id(userApplication.getId())
                .username(userApplication.getUsername())
                .activationStatus(userApplication.getActivationStatus())
                .authorities(userApplication.getAuthorities())
                .build();
    }

    UserLoginDto toLoginDto(final UserApplication userApplication) {
        return UserLoginDto.builder()
                .username(userApplication.getUsername())
                .password(userApplication.getPassword())
                .authorities(userApplication.getAuthorities())
                .build();
    }
}
