package com.haredev.library.book.domain;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import com.haredev.library.infrastructure.errors.ErrorsConsumer;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    Either<ErrorsConsumer, BookResponse> addBook(BookRequest request) {
        return null;
    }
}
