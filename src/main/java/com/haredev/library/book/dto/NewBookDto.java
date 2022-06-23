package com.haredev.library.book.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NewBookDto {
    private final UUID bookId;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Integer yearPublication;
    private Integer pageNumber;
    private String language;
    private BookType bookType;
    private BookCover bookCover;
    private BookStatus bookStatus;
    private String description;
}
