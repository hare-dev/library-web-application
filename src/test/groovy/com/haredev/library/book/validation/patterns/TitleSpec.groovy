package com.haredev.library.book.validation.patterns

import com.google.common.base.Strings
import com.haredev.library.book.controller.validation.patterns.Title
import spock.lang.Specification

class TitleSpec extends Specification {
    def "Title validation - valid"() {
        given:
        final int validTitleLength = 80
        final String validTitle = Strings.repeat("*" as String, validTitleLength)

        when:
        def validation = Title.validate(validTitle)

        then:
        validation.isValid()
    }

    def "Title validation - invalid"() {
        given:
        final int invalidTitleLength = 110
        final String invalidTitle = Strings.repeat("*" as String, invalidTitleLength)

        when:
        def validation = Title.validate(invalidTitle)

        then:
        validation.isInvalid()
    }
}
