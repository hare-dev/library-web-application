package com.haredev.library.book.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookDTO {
    private final UUID bookId;
    private String title;
    private String author;
    private String description;
    private Integer yearPublication;
    private Integer pageNumber;
    private String isbn;
    private String publisher;
    private String language;
    private BookType bookType;
    private BookCover bookCover;
    private BookStatus bookStatus;
}
