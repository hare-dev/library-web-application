package com.haredev.library.book.validation.patterns

import com.google.common.base.Strings
import com.haredev.library.book.controller.validation.patterns.Author
import spock.lang.Specification

class AuthorSpec extends Specification {
    def "Author validation - valid"() {
        given:
        final int validAuthorLength = 40
        final String validAuthor = Strings.repeat("*" as String, validAuthorLength)

        when:
        def validation = Author.validate(validAuthor)

        then:
        validation.isValid()
    }

    def "Author validation - invalid"() {
        given:
        final int invalidAuthorLength = 80
        final String invalidAuthor = Strings.repeat("*" as String, invalidAuthorLength)

        when:
        def validation = Author.validate(invalidAuthor)

        then:
        validation.isInvalid()
    }
}
