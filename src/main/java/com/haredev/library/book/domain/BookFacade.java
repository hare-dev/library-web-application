package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookError;
import com.haredev.library.book.dto.NewBookDto;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;
    private final BookValidation bookValidation;

    Either<BookError, NewBookDto> addBook(NewBookDto newBookDto) {
        Book book = bookCreator.fromDTO(newBookDto);
        return bookValidation
                .validate(book)
                .map(book -> bookRepository.save(book)).map(book.toDTO());
    }
}
