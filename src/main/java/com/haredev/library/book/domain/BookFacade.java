package com.haredev.library.book.domain;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    public BookResponse addBook(BookRequest request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book).response();
    }
}
