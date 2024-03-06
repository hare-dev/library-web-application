package com.haredev.library.book.domain.dto.update;

import com.haredev.library.book.domain.api.BookCategory;
import com.haredev.library.book.domain.api.BookCover;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.hibernate.validator.constraints.ISBN;
import org.hibernate.validator.constraints.Length;

@Builder
public record BookUpdateDto(
        @NotNull(message = "Invalid Title: Title cannot be null")
        @Length(min = 0, max = 50, message = "Invalid Title: Title must be of 0 - 50 characters")
        String title,
        @NotNull(message = "Invalid Author: Author cannot be null")
        @Length(min = 0, max = 50, message = "Author must be of 0 - 50 characters")
        String author,
        @ISBN(message = "Invalid ISBN format")
        @NotNull(message = "Invalid ISBN: ISBN cannot be null")
        String isbn,
        @NotNull(message = "Invalid Publisher: Publisher cannot be null")
        @Length(min = 0, max = 50, message = "Publisher must be of 0 - 50 characters")
        String publisher,
        @NotNull(message = "Invalid Year Publication: Year Publication cannot be null")
        Integer yearPublication,
        @NotNull(message = "Invalid Page Number: Page Number cannot be null")
        Integer pageNumber,
        @NotNull(message = "Invalid Language: Language cannot be null")
        @Length(min = 0, max = 50, message = "Language must be of 0 - 50 characters")
        String language,
        @NotNull(message = "Invalid Book Category: Book Category cannot be null")
        BookCategory bookCategory,
        @NotNull(message = "Invalid Book Cover: Book Cover cannot be null")
        BookCover bookCover,
        @NotNull(message = "Invalid Description: Description cannot be null")
        @Length(min = 0, max = 200, message = "Description must be of 0 - 200 characters")
        String description
) { }
