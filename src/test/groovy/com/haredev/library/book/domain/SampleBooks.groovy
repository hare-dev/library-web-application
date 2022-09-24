package com.haredev.library.book.domain

import com.haredev.library.book.dto.BookCreateDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleBooks {
    static BookCreateDto createBookSample(Long bookId, String title, String author) {
        return BookCreateDto.builder()
                .bookId(bookId)
                .title(title)
                .author(author)
                .build()
    }
}

