package com.haredev.library.user.domain.api;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserError implements ResponseError {
    USERNAME_IS_NULL("Username is null", 400),
    PASSWORD_IS_NULL("Password is null", 400),
    USERNAME_IS_EMPTY("Username is empty", 400),
    PASSWORD_IS_EMPTY("Password is empty", 400),
    USER_NOT_FOUND("User not found", 404),
    USERNAME_DUPLICATED("Username is duplicated", 400);

    private final String message;
    private final int httpCode;

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public int getHttpCode() {
        return 0;
    }
}
