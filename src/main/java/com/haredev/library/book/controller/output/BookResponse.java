package com.haredev.library.book.controller.output;

import com.haredev.library.book.dto.BookCover;
import com.haredev.library.book.dto.BookStatus;
import com.haredev.library.book.dto.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Builder
@Getter
public class BookResponse {
    private final UUID bookId;
    private final String title;
    private final String author;
    private final String isbn;
    private final String publisher;
    private final Integer yearPublication;
    private final Integer pageNumber;
    private final String language;
    private final BookType bookType;
    private final BookCover bookCover;
    private final BookStatus bookStatus;
    private final String description;
}
