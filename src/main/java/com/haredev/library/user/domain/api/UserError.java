package com.haredev.library.user.domain.api;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserError implements ResponseError {
    USER_NOT_FOUND("User not found", 404),
    DUPLICATED_USERNAME("Username is duplicated", 400),
    EMPTY_USERNAME("Username cannot be empty", 400),
    NULL_USERNAME("Username cannot be null", 400),
    NULL_PASSWORD("Password cannot be null", 400),
    PASSWORD_IS_TOO_WEAK("Password is too weak, must meet the conditions:" +
            "Password must be 8 or more characters in length, " +
            "Password must contain 1 or more uppercase characters, " +
            "Password must contain 2 or more digit characters, " +
            "Password must contain 2 or more special characters", 400);

    private final String message;
    private final int httpCode;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }
}
