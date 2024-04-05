package com.haredev.library.user.controller.output;

import com.haredev.library.user.domain.api.AccountStatus;
import lombok.Builder;

@Builder
public record RegistrationResponse(
    Long id,
    String username,
    String email,
    AccountStatus accountStatus,
    String verificationToken
) { }
