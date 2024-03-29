package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.BookCreateDto;

class BookMapper {
    BookCreateDto toBookCreateResponse(Book book) {
        return BookCreateDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publisher(book.getPublisher())
                .yearPublication(book.getYearPublication())
                .pageNumber(book.getPageNumber())
                .language(book.getLanguage())
                .bookCategory(book.getBookCategory())
                .bookCover(book.getBookCover())
                .bookStatus(book.getBookStatus())
                .description(book.getDescription())
                .build();
    }
}
