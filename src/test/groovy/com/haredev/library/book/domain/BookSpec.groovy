package com.haredev.library.book.domain

import com.haredev.library.book.domain.api.error.BookError
import com.haredev.library.book.domain.dto.BookCreateDto

import com.haredev.library.book.samples.SampleBooks
import io.vavr.control.Either
import spock.lang.Specification
import static com.haredev.library.book.domain.api.error.BookError.BOOK_NOT_FOUND
import static com.haredev.library.book.domain.api.error.BookError.CATEGORY_NOT_FOUND

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade()

    def final twilight = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def "Should be empty"() {
        expect:
        facade.fetchAllBooks().isEmpty()
    }

    def "Should add one book"() {
        given: "Should add book to system"
        facade.addBook(twilight)

        when: "Should have one book"
        Either<BookError, BookCreateDto> result = facade.findBookById(twilight.bookId)

        then: "Should return the book"
        result.get() == twilight
    }

    def "Should have two books"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should have two books"
        Either<BookError, BookCreateDto> twilight_result = facade.findBookById(twilight.bookId)
        Either<BookError, BookCreateDto> django_result = facade.findBookById(django.bookId)

        then: "Should return two books"
        twilight_result.get() == twilight
        django_result.get() == django
    }

    def "Should return all books"() {
        given: "Should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should return list of books"
        List<BookCreateDto> foundBooks = facade.fetchAllBooks()

        then: "Should return books we have added"
        foundBooks.contains(twilight)
        foundBooks.contains(django)
    }

    def "Should remove one book"() {
        given: "Should add one book"
        facade.addBook(twilight)

        when: "Should remove one book"
        facade.removeBookById(twilight.bookId)

        then: "Should be empty"
        facade.fetchAllBooks().isEmpty()
    }

    def "Should remove two books"() {
        given: "Should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should remove two books"
        facade.removeBookById(django.bookId)
        facade.removeBookById(twilight.bookId)

        then: "Should be empty"
        facade.fetchAllBooks().isEmpty()
    }

    def "Should not find book by not exist id in database"() {
        given: "Set up not exist book id in database"
        final Long ID_BOOK_NOT_EXIST = 9999

        when: "Should find book"
        BookError bookError = facade.findBookById(ID_BOOK_NOT_EXIST).getLeft()

        then: "Should return error response as BOOK_NOT_FOUND"
        BOOK_NOT_FOUND == bookError
    }
}
