package com.haredev.library.book.controller.input;

import com.haredev.library.book.dto.BookCover;
import com.haredev.library.book.dto.BookStatus;
import com.haredev.library.book.dto.BookType;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
public class BookRequest {
    private UUID bookId;
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
