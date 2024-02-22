package com.haredev.library.user.domain.api;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserError implements ResponseError {
    USER_NOT_FOUND("User not found", 404),
    DUPLICATED_USERNAME("Username is duplicated", 400),
    USER_IS_ALREADY_ADMIN("Cannot promote admin to be admin", 400);

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
