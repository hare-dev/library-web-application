package com.haredev.library.book.domain;

import com.haredev.library.book.domain.dto.NewBookDto;

class BookCreator {
    Book fromDTO(NewBookDto bookDto) {
        return Book.builder()
                .bookId(bookDto.getBookId())
                .title(bookDto.getTitle())
                .author(bookDto.getAuthor())
                .isbn(bookDto.getIsbn())
                .publisher(bookDto.getPublisher())
                .yearPublication(bookDto.getYearPublication())
                .pageNumber(bookDto.getPageNumber())
                .language(bookDto.getLanguage())
                .bookType(bookDto.getBookType())
                .bookCover(bookDto.getBookCover())
                .bookCover(bookDto.getBookCover())
                .description(bookDto.getDescription())
                .build();
    }
}
