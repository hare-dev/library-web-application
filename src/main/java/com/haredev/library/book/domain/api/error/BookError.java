package com.haredev.library.book.domain.api.error;

import com.haredev.library.infrastructure.errors.ResponseError;

public enum BookError implements ResponseError {
    BOOK_NOT_FOUND("Book not found in database", 404),
    NULL_OR_EMPTY_DESCRIPTION("Description is null or empty", 400),
    NULL_DATE_ADDED("Date added is null", 400);

    private final int httpCode;
    private final String message;

    BookError(String message, int httpCode) {
        this.httpCode = httpCode;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }
}
