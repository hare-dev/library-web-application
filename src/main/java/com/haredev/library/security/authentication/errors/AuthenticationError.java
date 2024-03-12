package com.haredev.library.security.authentication.errors;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum AuthenticationError implements ResponseError {
    WRONG_AUTHENTICATION_LOGIN_OR_PASSWORD("Wrong authentication login or password", 403);

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
