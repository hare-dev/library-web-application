package com.haredev.library.user.controller.input;

import com.haredev.library.user.controller.input.validation.password.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegistrationRequest(
    Long userId,
    @NotNull
    @NotBlank
    String username,
    @NotNull
    @NotBlank
    @Password
    String password
) { }
