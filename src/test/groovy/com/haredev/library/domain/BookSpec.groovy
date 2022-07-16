package com.haredev.library.domain

import com.haredev.library.book.domain.BookConfiguration
import com.haredev.library.book.domain.BookFacade
import spock.lang.Specification

class BookSpec extends Specification implements SampleBooks {
    BookFacade facade = new BookConfiguration().bookFacade()

    def "Add one book to system"() {
        given: "Add book to system"
        facade.addBook(twilight)

        expect: "System should have one book"
        facade.findBook(twilight.bookId).equals(twilight)
    }

    def "Remove book from system"() {
        given: "Add one book to system"
        facade.addBook(twilight)

        when: "Remove one book from system"
        facade.removeBook(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks().size() == 0
    }

    def "Add two books to system"() {
        given: "Add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        expect: "System should have two books"
        facade.fetchAllBooks().size() == 2
    }

    def "Remove two books from system"() {
        given: "Add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Remove two books from system"
        facade.removeBook(django.bookId)
        facade.removeBook(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks().size() == 0
    }
}
