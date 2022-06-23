package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookError;
import com.haredev.library.book.dto.NewBookDto;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.ISBNValidator;

import java.util.function.Predicate;

import static io.vavr.API.*;

@AllArgsConstructor
final class BookValidation {
    private final ISBNValidator validator;

    Either<BookError, NewBookDto> validate(Book book) {
        return Match(book).of(
                Case($(checkIsbn(book.getIsbn())), Either.left(BookError.ISBN_CODE_IS_INVALID)),
                Case($(), Either.right(book.toDTO()))
        );
    }

    private Predicate<Book> checkIsbn(String isbn) {
        return book -> validator.isValid(book.getIsbn());
    }
}
