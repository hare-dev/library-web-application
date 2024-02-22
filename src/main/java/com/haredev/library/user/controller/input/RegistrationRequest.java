package com.haredev.library.user.controller.input;

import com.haredev.library.user.controller.input.validation.password.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegistrationRequest(
    Long userId,
    @NotNull
    @NotBlank(message = "Username cannot be empty")
    String username,
    @NotNull
    @NotBlank(message = "Password cannot be empty")
    @Password
    String password,
    @Email(message = "Email format is not valid")
    String email
) { }
