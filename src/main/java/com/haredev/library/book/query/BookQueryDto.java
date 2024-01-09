package com.haredev.library.book.query;

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
