package com.haredev.library.book.domain;

import com.haredev.library.book.controller.input.BookRequest;

class BookCreator {
    Book from(BookRequest request) {
        return Book.builder()
                .bookId(request.getBookId())
                .title(request.getTitle())
                .author(request.getAuthor())
                .isbn(request.getIsbn())
                .publisher(request.getPublisher())
                .yearPublication(request.getYearPublication())
                .pageNumber(request.getPageNumber())
                .language(request.getLanguage())
                .bookType(request.getBookType())
                .bookCover(request.getBookCover())
                .bookCover(request.getBookCover())
                .description(request.getDescription())
                .build();
    }
}
