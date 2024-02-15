package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

import static com.haredev.library.book.domain.api.error.BookError.NULL_DATE_ADDED;
import static com.haredev.library.book.domain.api.error.BookError.NULL_OR_EMPTY_DESCRIPTION;
import static io.vavr.API.$;
import static io.vavr.API.Case;

@RequiredArgsConstructor
class CommentValidation {
    public final Either<BookError, CommentCreateDto> validateParameters(final CommentCreateDto request) {
        return API.Match(request)
                .option(
                        Case($(argument -> Objects.isNull(argument.description())), NULL_OR_EMPTY_DESCRIPTION),
                        Case($(argument -> Objects.isNull(argument.createdTime())), NULL_DATE_ADDED),
                        Case($(argument -> argument.description().isEmpty()), NULL_OR_EMPTY_DESCRIPTION))
                .toEither(request)
                .swap();
    }
}
