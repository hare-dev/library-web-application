package com.haredev.library.book.samples

import com.haredev.library.book.domain.dto.BookCreateDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleBooks {
    static BookCreateDto createBookSample(Long bookId, String title, String author) {
        return BookCreateDto.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .build()
    }
}

