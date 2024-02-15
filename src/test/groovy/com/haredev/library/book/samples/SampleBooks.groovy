package com.haredev.library.book.samples

import com.haredev.library.book.domain.api.BookCover
import com.haredev.library.book.domain.dto.BookCreateDto
import com.haredev.library.book.domain.dto.BookUpdateDto
import groovy.transform.CompileStatic

@CompileStatic
final class SampleBooks {
    static final Long notExistBookWithThisId = 5000L

    static BookCreateDto createBookSampleToUpdate(Long bookId, String title, String author, String isbn) {
        return BookCreateDto.builder()
                .id(bookId)
                .title(title)
                .author(author)
                .isbn(isbn)
                .build()
    }

    static BookCreateDto createBookSampleToUpdate() {
        return BookCreateDto.builder()
                .id(1L)
                .title("EXAMPLE_TITLE")
                .author("EXAMPLE_AUTHOR")
                .isbn("EXAMPLE_ISBN")
                .description("EXAMPLE_DESCRIPTION")
                .yearPublication(2000)
                .bookCover(BookCover.SOFT)
                .build()
    }

    static BookUpdateDto createBookWithDataToUpdateSample() {
        return BookUpdateDto.builder()
                .title("UPDATED_TITLE")
                .author("UPDATED_AUTHOR")
                .isbn("UPDATED_ISBN")
                .description("UPDATED_DESCRIPTION")
                .yearPublication(2023)
                .bookCover(BookCover.HARD)
                .build()
    }

    static BookCreateDto createBookSampleWithTheSameIsbn() {
        return BookCreateDto.builder()
                .id(1L)
                .isbn("EXAMPLE_ISBN")
                .build()
    }
}

