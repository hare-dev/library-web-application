package com.haredev.library.domain

import com.haredev.library.book.controller.output.BookResponse
import groovy.transform.CompileStatic

@CompileStatic
trait SampleBooks {
    BookResponse twilight = createBookSample("Twilight", "Stephenie Meyer")
    static private BookResponse createBookSample(String title, String author) {
        return BookResponse.builder()
                .title(title)
                .author(author)
                .build()
    }
}
