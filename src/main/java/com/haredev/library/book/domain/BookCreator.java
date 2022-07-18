package com.haredev.library.book.domain;

import com.haredev.library.book.dto.BookCreateDto;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

class BookCreator {
    Book from(BookCreateDto bookCreateDto) {
        requireNonNull(bookCreateDto);
        return Book.builder()
                .bookId(String.valueOf(UUID.randomUUID()))
                .title(bookCreateDto.getTitle())
                .author(bookCreateDto.getAuthor())
                .isbn(bookCreateDto.getIsbn())
                .publisher(bookCreateDto.getPublisher())
                .yearPublication(bookCreateDto.getYearPublication())
                .pageNumber(bookCreateDto.getPageNumber())
                .language(bookCreateDto.getLanguage())
                .bookType(bookCreateDto.getBookType())
                .bookCover(bookCreateDto.getBookCover())
                .bookStatus(bookCreateDto.getBookStatus())
                .description(bookCreateDto.getDescription())
                .build();
    }
}
