package com.haredev.library.book.domain.dto;

import com.haredev.library.book.domain.api.BookCategory;
import com.haredev.library.book.domain.api.BookCover;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BookUpdateDto {
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final Integer yearPublication;
    private final Integer pageNumber;
    private final String language;
    private final BookCategory bookCategory;
    private final BookCover bookCover;
    private final String description;
}
