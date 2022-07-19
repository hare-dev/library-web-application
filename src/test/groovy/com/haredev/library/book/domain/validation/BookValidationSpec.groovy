package com.haredev.library.book.domain.validation

import com.haredev.library.book.dto.BookCreateDto
import com.haredev.library.book.controller.validation.BookValidation
import io.vavr.control.Validation
import spock.lang.Specification

class BookValidationSpec extends Specification {
    BookValidation bookValidation;

    def "All fields of validation are valid"() {
        given:
        def request = BookCreateDto.builder()
                .title("Twilight")
                .author("Stephenie Meyer")
                .isbn("0-596-52068-9")
                .publisher("Meyer Novel")
                .pageNumber(200)
                .language("English")
                .description("Very good book with fantastic history!")
                .build()

        when:
        def validation = bookValidation.validate(request)

        then:
        validation == Validation.valid(BookCreateDto.builder()
                .title("Twilight")
                .author("Stephenie Meyer")
                .isbn("0-596-52068-9")
                .publisher("Meyer Novel")
                .pageNumber(200)
                .language("English")
                .description("Very good book with fantastic history!")
                .build())
    }
}
