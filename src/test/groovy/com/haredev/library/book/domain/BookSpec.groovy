package com.haredev.library.book.domain

import com.haredev.library.book.domain.api.error.BookError
import com.haredev.library.book.domain.dto.BookCreateDto
import com.haredev.library.book.domain.dto.CommentCreateDto
import com.haredev.library.book.samples.SampleBooks
import com.haredev.library.book.samples.SampleComments
import io.vavr.control.Either
import spock.lang.Specification

import java.time.LocalDateTime

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository())

    def final int page = 5;

    def final twilight = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final twilightComment = SampleComments.createCommentSample(0L, twilight.bookId, "Best book!", LocalDateTime.now())
    def final twilightCommentWithNullDescription = SampleComments.createCommentSample(0L, twilight.bookId, null, LocalDateTime.now())
    def final twilightCommentWithEmptyDescription = SampleComments.createCommentSample(0L, twilight.bookId, "", LocalDateTime.now())
    def final twilightCommentWithNullDateAdded = SampleComments.createCommentSample(0L, twilight.bookId, "Best book!", null)

    def "Should be empty"() {
        expect:
        facade.fechAllBooks(page).isEmpty()
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
        List<BookCreateDto> foundBooks = facade.fechAllBooks(page)

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
        facade.fechAllBooks(page).isEmpty()
    }

    def "Should remove two books"() {
        given: "Should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should remove two books"
        facade.removeBookById(django.bookId)
        facade.removeBookById(twilight.bookId)

        then: "Should be empty"
        facade.fechAllBooks(page).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        facade.addCommentToBook(twilightComment)

        then: "Should return comment"
        Either<BookError, CommentCreateDto> twilightCommentAdded = facade.findCommentById(twilightComment.commentId)
        twilightComment.description == twilightCommentAdded.get().description
    }

    def "Should not add comment to book because description is null"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentCreateDto> response = facade.addCommentToBook(twilightCommentWithNullDescription)

        then: "Should return error with null description"
        BookError.NULL_OR_EMPTY_DESCRIPTION == response.getLeft()
    }

    def "Should not add comment to book because description is empty"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentCreateDto> response = facade.addCommentToBook(twilightCommentWithEmptyDescription)

        then: "Should return error with null description"
        BookError.NULL_OR_EMPTY_DESCRIPTION == response.getLeft()
    }

    def "Should not add comment to book because date added is null"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentCreateDto> response = facade.addCommentToBook(twilightCommentWithNullDateAdded)

        then: "Should return error with null description"
        BookError.NULL_DATE_ADDED == response.getLeft()
    }

}
