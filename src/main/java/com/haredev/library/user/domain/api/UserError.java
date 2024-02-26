package com.haredev.library.user.domain.api;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserError implements ResponseError {
    USER_NOT_FOUND("User not found", 404),
    DUPLICATED_USERNAME("Username is duplicated", 400),
    USER_IS_ALREADY_ADMIN("Cannot promote admin to be admin", 400),
    CONFIRMATION_TOKEN_NOT_FOUND("Confirmation token not found", 404),
    CONFIRMATION_TOKEN_IS_EXPIRED("Confirmation token is expired", 400),
    CONFIRMATION_TOKEN_IS_ALREADY_CONFIRMED("Confirmation token is confirmed", 400);


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
