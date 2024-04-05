package com.haredev.library.user.domain;

import com.haredev.library.user.controller.output.RegistrationResponse;
import com.haredev.library.user.domain.dto.UserPublicDetailsDto;
import com.haredev.library.user.domain.dto.UserLoginDto;

class UserMapper {
    RegistrationResponse toRegistrationResponse(final UserApplication userApplication,
                                                final String verificationToken) {
        return RegistrationResponse.builder()
                .id(userApplication.getId())
                .username(userApplication.getUsername())
                .email(userApplication.getEmail())
                .accountStatus(userApplication.getAccountStatus())
                .verificationToken(verificationToken)
                .build();
    }

    UserPublicDetailsDto toUserDetailsDto(final UserApplication userApplication) {
        return UserPublicDetailsDto.builder()
                .id(userApplication.getId())
                .username(userApplication.getUsername())
                .accountStatus(userApplication.getAccountStatus())
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
