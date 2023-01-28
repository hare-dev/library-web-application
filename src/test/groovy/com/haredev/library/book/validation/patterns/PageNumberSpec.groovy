package com.haredev.library.book.validation.patterns

import com.haredev.library.book.controller.validation.patterns.PageNumber
import spock.lang.Specification

class PageNumberSpec extends Specification {
    def "PageNumber validation - valid"() {
        given:
        final Integer validPageNumber = 200

        when:
        def validation = PageNumber.validate(validPageNumber)

        then:
        validation.isValid()
    }

    def "PageNumber validation - invalid"() {
        given:
        final Integer invalidPageNumber = null;

        when:
        def validation = PageNumber.validate(invalidPageNumber)

        then:
        validation.isInvalid()
    }
}
