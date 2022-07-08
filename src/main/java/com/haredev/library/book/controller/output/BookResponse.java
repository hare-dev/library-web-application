package com.haredev.library.book.controller.output;

import com.haredev.library.book.dto.BookStatus;
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
    private final BookStatus bookStatus;
}
