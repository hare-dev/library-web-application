package com.haredev.library.book.domain.api;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookError implements ResponseError {
    ISBN_CODE_IS_INVALID("Isbn code is invalid", 400);

    private final String message;
    private final int code;

    @Override
    public int getHttpCode() {
        return code;
    }
}
