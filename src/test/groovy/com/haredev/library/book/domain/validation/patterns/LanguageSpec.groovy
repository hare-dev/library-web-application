package com.haredev.library.book.domain.validation.patterns

import com.google.common.base.Strings
import com.haredev.library.book.controller.validation.patterns.Language
import spock.lang.Specification

class LanguageSpec extends Specification {
    def "Language validation - valid"() {
        given:
        final int validLanguageLength = 20
        final String validLanguage = Strings.repeat("*" as String, validLanguageLength)

        when:
        def validation = Language.validate(validLanguage)

        then:
        validation.isValid()
    }

    def "Language validation - invalid"() {
        given:
        final int invalidLanguageLength = 70
        final String invalidLanguage = Strings.repeat("*" as String, invalidLanguageLength)

        when:
        def validation = Language.validate(invalidLanguage)

        then:
        validation.isInvalid()
    }
}
