package com.haredev.library.book.domain
import spock.lang.Specification

import static java.util.UUID.randomUUID

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    def final twilight = SampleBooks.createBookSample(randomUUID().toString(), "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(randomUUID().toString(), "Django", "Quentin Tarantino")

    def "System should have one book"() {
        given: "Should add book to system"
        facade.addBook(twilight)

        expect: "System should have one book"
        facade.fetchAllBooks().size() == 1
    }

    def "System should be empty"() {
        expect: facade.fetchAllBooks().isEmpty()
    }

    def "Should remove book from system"() {
        given: "Should add one book to system"
        final String twilightBookId = facade.addBook(twilight).bookId

        when: "Should remove one book from system"
        facade.removeBook(twilightBookId)

        then: "System should be empty"
        facade.fetchAllBooks().size() == 0
    }

    def "Should have two books to system"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        expect: "System should have two books"
        facade.fetchAllBooks().size() == 2
    }

    def "Remove two books from system"() {
        given: "Should add two books to system"
        final String twilightBookId = facade.addBook(twilight).bookId
        final String djangoBookId = facade.addBook(django).bookId

        when: "Should remove two books from system"
        facade.removeBook(twilightBookId)
        facade.removeBook(djangoBookId)

        then: "System should be empty"
        facade.fetchAllBooks().size() == 0
    }
}
