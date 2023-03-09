package com.haredev.library.book.query;

import com.haredev.library.book.domain.api.BookCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class BookQueryDto {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
}
