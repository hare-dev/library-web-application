package com.haredev.library.book.domain.validation.patterns

import com.haredev.library.book.controller.validation.patterns.Isbn
import spock.lang.Specification

class IsbnSpec extends Specification {
    def "Isbn validation - valid"() {
        given:
        final String validIsbn = "ISBN 978-0-596-52068-7"

        when:
        def validation = Isbn.validate(validIsbn)

        then:
        validation.isValid()
    }

    def "Isbn validation - invalid"() {
        given:
        final String invalidIsbn = "INVALID_ISBN"

        when:
        def validation = Isbn.validate(invalidIsbn)

        then:
        validation.isInvalid()
    }
}
