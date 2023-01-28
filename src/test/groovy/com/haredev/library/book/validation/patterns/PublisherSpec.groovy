package com.haredev.library.book.validation.patterns

import com.google.common.base.Strings
import com.haredev.library.book.controller.validation.patterns.Publisher
import spock.lang.Specification

class PublisherSpec extends Specification {
    def "Publisher validation - valid"() {
        given:
        final int validPublisherLength = 20
        final String validPublisher = Strings.repeat("*" as String, validPublisherLength)

        when:
        def validation = Publisher.validate(validPublisher)

        then:
        validation.isValid()
    }

    def "Publisher validation - invalid"() {
        given:
        final int invalidPublisherLength = 60
        final String invalidPublisher = Strings.repeat("*" as String, invalidPublisherLength)

        when:
        def validation = Publisher.validate(invalidPublisher)

        then:
        validation.isInvalid()
    }
}
