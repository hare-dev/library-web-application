package com.haredev.library.book.controller.input;

import com.haredev.library.book.dto.BookCover;
import com.haredev.library.book.dto.BookStatus;
import com.haredev.library.book.dto.BookType;
import lombok.*;

@Getter
@Builder
public class BookRequest {
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

    public BookRequest(String title, String author, String isbn, String publisher,
                       Integer yearPublication, Integer pageNumber, String language,
                       BookType bookType, BookCover bookCover, BookStatus bookStatus, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisher = publisher;
        this.yearPublication = yearPublication;
        this.pageNumber = pageNumber;
        this.language = language;
        this.bookType = bookType;
        this.bookCover = bookCover;
        this.bookStatus = bookStatus;
        this.description = description;
    }
}
