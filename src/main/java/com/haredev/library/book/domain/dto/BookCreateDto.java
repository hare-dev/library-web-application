package com.haredev.library.book.domain.dto;

import com.haredev.library.book.domain.api.BookCategory;
import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.domain.api.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
public class BookCreateDto {
    private final Long id;
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final Integer yearPublication;
    private final Integer pageNumber;
    private final String language;
    private final BookCategory bookCategory;
    private final BookCover bookCover;
    private final BookStatus bookStatus;
    private final String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookCreateDto that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
