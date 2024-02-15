package com.haredev.library.book.domain.dto;

import com.haredev.library.book.domain.api.BookCategory;
import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.domain.api.BookStatus;
import lombok.Builder;

@Builder
public record BookCreateDto(
        Long id,
        String title,
        String author,
        String isbn,
        String publisher,
        Integer yearPublication,
        Integer pageNumber,
        String language,
        BookCategory bookCategory,
        BookCover bookCover,
        BookStatus bookStatus,
        String description
) { }
