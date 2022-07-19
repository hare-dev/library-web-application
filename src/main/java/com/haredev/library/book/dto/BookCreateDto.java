package com.haredev.library.book.dto;
import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.domain.api.BookStatus;
import com.haredev.library.book.domain.api.BookType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class BookCreateDto {
    private String bookId;
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
