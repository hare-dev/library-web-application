package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.BookCreateDto;

import static java.util.Objects.requireNonNull;

class BookCreator {
    Book from(final BookCreateDto request) {
        requireNonNull(request);
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .author(request.author())
                .isbn(request.isbn())
                .publisher(request.publisher())
                .yearPublication(request.yearPublication())
                .pageNumber(request.pageNumber())
                .language(request.language())
                .bookCategory(request.bookCategory())
                .bookCover(request.bookCover())
                .bookStatus(request.bookStatus())
                .description(request.description())
                .build();
    }
}
