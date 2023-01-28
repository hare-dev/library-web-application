package com.haredev.library.book.domain.api.error;

import com.haredev.library.infrastructure.errors.ResponseError;

public enum BookError implements ResponseError {
    BOOK_NOT_FOUND("Book not found in database", 404),
    CATEGORY_NOT_FOUND("Category not found in database", 404);

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
