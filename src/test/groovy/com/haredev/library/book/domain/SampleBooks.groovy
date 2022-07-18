package com.haredev.library.domain

import com.haredev.library.book.controller.input.BookRequest
import groovy.transform.CompileStatic

@CompileStatic
trait SampleBooks {
    BookRequest twilight = createBookSample("Twilight", "Stephenie Meyer")
    BookRequest django = createBookSample("Django", "Quentin Tarantino")

    static private BookRequest createBookSample(String title, String author) {
        return BookRequest.builder()
                .title(title)
                .author(author)
                .build()
    }
}
