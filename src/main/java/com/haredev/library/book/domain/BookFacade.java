package com.haredev.library.book.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;
}
