package com.haredev.library.book.validation

import com.google.common.base.Strings
import com.haredev.library.book.domain.BookConfiguration
import com.haredev.library.book.domain.dto.BookCreateDto
import spock.lang.Specification

class BookValidationSpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    def "Valid path of book validation"() {
        given:
        def request = BookCreateDto.builder()
                .title("Twilight")
                .author("Stephenie Meyer")
                .isbn("ISBN 978-0-596-52068-7")
                .publisher("Meyer Novel")
                .pageNumber(200)
                .language("English")
                .description("Very good book with fantastic history!")
                .build()
        when:
        def validation = facade.validateBook(request)

        then:
        validation.isValid()
    }

    def "Invalid path of book validation with all invalid fields"() {

        given:
        final invalidTitle = Strings.repeat("*" as String, 110)
        final invalidAuthor = Strings.repeat("*" as String, 60)
        final invalidPublisher = Strings.repeat("*" as String, 60)
        final invalidLanguage = Strings.repeat("*" as String, 40)
        final invalidDescription = Strings.repeat("*" as String, 220)
        final invalidPageNumber = null;
        final invalidIsbn = "INVALID_ISBN"

        def request = BookCreateDto.builder()
                .title(invalidTitle)
                .author(invalidAuthor)
                .isbn(invalidIsbn)
                .publisher(invalidPublisher)
                .pageNumber(invalidPageNumber)
                .language(invalidLanguage)
                .description(invalidDescription)
                .build()
        when:
        def validation = facade.validateBook(request)

        then:
        validation.isInvalid()
    }

    def "Invalid path of book validation with isbn code invalid"() {

        given:
        final invalidIsbn = "INVALID_ISBN"

        def request = BookCreateDto.builder()
                .title("Twilight")
                .author("Stephenie Meyer")
                .isbn(invalidIsbn)
                .publisher("Meyer Novel")
                .pageNumber(200)
                .language("English")
                .description("Very good book with fantastic history!")
                .build()
        when:
        def validation = facade.validateBook(request)

        then:
        validation.isInvalid()
    }
}