package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.BookCreateDto;

import static java.util.Objects.requireNonNull;

class BookCreator {
    Book from(BookCreateDto request) {
        requireNonNull(request);
        return Book.builder()
                .id(request.getId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publisher(request.getPublisher())
                .yearPublication(request.getYearPublication())
                .pageNumber(request.getPageNumber())
                .language(request.getLanguage())
                .bookCategory(request.getBookCategory())
                .bookCover(request.getBookCover())
                .bookStatus(request.getBookStatus())
                .description(request.getDescription())
                .build();
    }
}
