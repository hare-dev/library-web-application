package com.haredev.library.book.dto;
import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.domain.api.BookStatus;
import com.haredev.library.book.domain.api.BookType;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
public class BookCreateDto {
    private final String bookId;
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
