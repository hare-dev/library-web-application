package com.haredev.library.book.domain.api.error;

import com.haredev.library.infrastructure.errors.ResponseError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BookError implements ResponseError {
    BOOK_NOT_FOUND("Book not found in system", 404),
    NULL_OR_EMPTY_DESCRIPTION("Description cannot be null or empty", 400),
    NULL_DATE_ADDED("Date added is null", 400),
    COMMENT_NOT_FOUND("Comment not found in in system", 404),
    ISBN_DUPLICATED("Isbn code cannot be duplicated", 400);

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