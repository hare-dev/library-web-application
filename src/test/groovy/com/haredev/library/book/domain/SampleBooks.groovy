package com.haredev.library.book.domain

import com.haredev.library.book.dto.BookCreateDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleBooks {
    static BookCreateDto createBookSample(String title, String author) {
        return BookCreateDto.builder()
                .bookId(UUID.randomUUID().toString())
                .title(title)
                .author(author)
                .build()
    }
}

