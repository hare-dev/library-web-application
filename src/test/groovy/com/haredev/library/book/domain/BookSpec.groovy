package com.haredev.library.book.domain

import com.haredev.library.book.dto.BookCreateDto
import io.vavr.control.Option
import spock.lang.Specification

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    private static final int PAGE_SIZE = 10

    def final twilight = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def "System should be empty"() {
        expect:
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }

    def "One book is in the system"() {
        given: "Should add book to system"
        facade.addBook(twilight)

        when: "System should have one book"
        Option<BookCreateDto> result = facade.findBookById(twilight.bookId)

        then:
        result.get() == twilight
    }

    def "Two books are in the system"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "System should return two books"
        Option<BookCreateDto> twilight_result = facade.findBookById(twilight.bookId)
        Option<BookCreateDto> django_result = facade.findBookById(django.bookId)

        then:
        twilight_result.get() == twilight
        django_result.get() == django
    }

    def "Should return all films from system"() {
        given: "System should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "We ask system to get books"
        List<BookCreateDto> foundBooks = facade.fetchAllBooks(PAGE_SIZE)

        then: "System should return books we have added"
        foundBooks.contains(twilight)
        foundBooks.contains(django)
    }

    def "Should remove book from system"() {
        given: "Should add one book to system"
        facade.addBook(twilight)

        when: "Should remove one book from system"
        facade.removeBookById(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }

    def "Should remove two books from system"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should remove two books from system"
        facade.removeBookById(django.bookId)
        facade.removeBookById(twilight.bookId)

        then: "System should be empty"
        facade.fetchAllBooks(PAGE_SIZE).isEmpty()
    }


}
