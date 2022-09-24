package com.haredev.library.book.domain.validation.patterns

import com.google.common.base.Strings
import com.haredev.library.book.controller.validation.patterns.Description
import spock.lang.Specification

class DescriptionSpec extends Specification {
    def "Description validation - valid"() {
        given:
        final int validDescriptionLength = 100
        final String validDescription = Strings.repeat("*" as String, validDescriptionLength)

        when:
        def validation = Description.validate(validDescription)

        then:
        validation.isValid()
    }

    def "Description validation - invalid"() {
        given:
        final int invalidDescriptionLength = 250
        final String invalidDescription = Strings.repeat("*" as String, invalidDescriptionLength)

        when:
        def validation = Description.validate(invalidDescription)

        then:
        validation.isInvalid()
    }
}
