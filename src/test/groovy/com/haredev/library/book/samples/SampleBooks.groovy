package com.haredev.library.book.samples

import com.haredev.library.book.domain.api.BookCover
import com.haredev.library.book.domain.dto.BookCreateDto
import com.haredev.library.book.domain.dto.update.BookUpdateDto
import groovy.transform.CompileStatic

@CompileStatic
trait SampleBooks {
    BookCreateDto twilight = createBookSample(1L, "Twilight", "Stephenie Meyer", "0-596-52068-9", 2000, BookCover.SOFT)
    BookCreateDto jumanji = createBookSample(2L, "Jumanji", "Van Allsburg Chris", "978 0 596 52068 7", 1996, BookCover.HARD)
    final Long bookWithThisIdNotExist = 999999L

    private BookCreateDto createBookSample(final Long id, final String title, final String author,
                                           final String isbn,
                                           final Integer yearPublication,
                                           final BookCover bookCover) {
        return BookCreateDto.builder()
                .id(id)
                .title(title)
                .author(author)
                .isbn(isbn)
                .yearPublication(yearPublication)
                .bookCover(bookCover)
                .build()
    }

    static BookCreateDto createBookSampleWithTheSameIsbn(final Long id) {
        return BookCreateDto.builder()
                .id(id)
                .isbn("the_same_example_isbn")
                .build()
    }

    static BookUpdateDto createBookSampleWithDataToUpdate() {
        return BookUpdateDto.builder()
                .title("updated_tittle")
                .author("updated_author")
                .isbn("updated_isbn")
                .yearPublication(1990)
                .bookCover(BookCover.HARD)
                .build()
    }
}