package com.haredev.library.book.domain

import com.haredev.library.book.domain.api.error.BookError
import com.haredev.library.book.domain.dto.BookCreateDto
import com.haredev.library.book.domain.dto.CommentDto
import com.haredev.library.book.samples.SampleBooks
import com.haredev.library.book.samples.SampleComments
import io.vavr.control.Either
import spock.lang.Specification

import java.time.LocalDateTime

import static com.haredev.library.book.domain.api.error.BookError.*

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository())

    def final PAGE = 5
    def final TWILIGHT = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final DJANGO = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final NOT_EXISTING_BOOK = SampleComments.createCommentSample(3L, 99999L, "NOT EXISTING", LocalDateTime.now())
    def final TWILIGHT_COMMENT_FIRST = SampleComments.createCommentSample(0L, TWILIGHT.bookId, "Best book!", LocalDateTime.now())
    def final TWILIGHT_COMMENT_SECOND = SampleComments.createCommentSample(1L, TWILIGHT.bookId, "Fantastic book!", LocalDateTime.now())
    def final TWILIGHT_COMMENT_WITH_NULL_DESCRIPTION = SampleComments.createCommentSample(0L, TWILIGHT.bookId, null, LocalDateTime.now())
    def final TWILIGHT_COMMENT_WITH_EMPTY_DESCRIPTION = SampleComments.createCommentSample(0L, TWILIGHT.bookId, "", LocalDateTime.now())
    def final TWILIGHT_COMMENT_WITH_NULL_DATE_ADDED = SampleComments.createCommentSample(0L, TWILIGHT.bookId, "Best book!", null)

    def "Should be empty"() {
        expect:
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add one book"() {
        given: "Should add book to system"
        facade.addBook(TWILIGHT)

        when: "Should have one book"
        Either<BookError, BookCreateDto> response = facade.findBookById(TWILIGHT.bookId)

        then: "Should return the book"
        response.get() == TWILIGHT
    }

    def "Should have two books"() {
        given: "Should add two books to system"
        facade.addBook(TWILIGHT)
        facade.addBook(DJANGO)

        when: "Should have two books"
        Either<BookError, BookCreateDto> twilight_response = facade.findBookById(TWILIGHT.bookId)
        Either<BookError, BookCreateDto> django_response = facade.findBookById(DJANGO.bookId)

        then: "Should return two books"
        twilight_response.get() == TWILIGHT
        django_response.get() == DJANGO
    }

    def "Should return all books"() {
        given: "Should add two books"
        facade.addBook(TWILIGHT)
        facade.addBook(DJANGO)

        when: "Should return list of books"
        List<BookCreateDto> foundBooks = facade.fetchAllBooks(PAGE)

        then: "Should return books we have added"
        foundBooks.contains(TWILIGHT)
        foundBooks.contains(DJANGO)
    }

    def "Should remove one book"() {
        given: "Should add one book"
        facade.addBook(TWILIGHT)

        when: "Should remove one book"
        facade.removeBookById(TWILIGHT.bookId)

        then: "Should be empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Should add two books"
        facade.addBook(TWILIGHT)
        facade.addBook(DJANGO)

        when: "Should remove two books"
        facade.removeBookById(DJANGO.bookId)
        facade.removeBookById(TWILIGHT.bookId)

        then: "Should be empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Should add book"
        facade.addBook(TWILIGHT)

        when: "Should add comment to book"
        Either<BookError, CommentDto> twilightCommentAdded = facade.addCommentToBook(TWILIGHT_COMMENT_FIRST)

        then: "Should return comment added to book"
        Either<BookError, CommentDto> twilightCommentFounded = facade.findCommentById(TWILIGHT_COMMENT_FIRST.commentId)
        twilightCommentAdded.get().description == twilightCommentFounded.get().description
    }

    def "Should not add comment to not existing book"() {
        when: "Should return error response"
        BookError errorResponse = facade.addCommentToBook(NOT_EXISTING_BOOK).getLeft()

        then:
        errorResponse == BOOK_NOT_FOUND
    }

    def "Should not add comment to book because description is null"() {
        given: "Should add book"
        facade.addBook(TWILIGHT)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(TWILIGHT_COMMENT_WITH_NULL_DESCRIPTION)

        then: "Should return error with null description"
        response.getLeft() == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because description is empty"() {
        given: "Should add book"
        facade.addBook(TWILIGHT)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(TWILIGHT_COMMENT_WITH_EMPTY_DESCRIPTION)

        then: "Should return error with null description"
        response.getLeft() == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because date added is null"() {
        given: "Should add book"
        facade.addBook(TWILIGHT)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(TWILIGHT_COMMENT_WITH_NULL_DATE_ADDED)

        then: "Should return error with null description"
        response.getLeft() == NULL_DATE_ADDED
    }

    def "Should get one comments from book"() {
        given: "Should add book and comment"
        facade.addBook(TWILIGHT)
        facade.addCommentToBook(TWILIGHT_COMMENT_FIRST)

        when: "Should find book and return list with comments"
        Either<BookError, List<CommentDto>> comments = facade.getBookByIdWithComments(TWILIGHT.bookId)

        then: "Should be one comment"
        comments.get().size() == 1
    }

    def "Should get two comments from book"() {
        given: "Should add book and comments"
        facade.addBook(TWILIGHT)
        facade.addCommentToBook(TWILIGHT_COMMENT_FIRST)
        facade.addCommentToBook(TWILIGHT_COMMENT_SECOND)

        when: "Should find book and return list with comments"
        Either<BookError, List<CommentDto>> response = facade.getBookByIdWithComments(TWILIGHT.bookId)

        then: "Book should have two comments"
        response.get().size() == 2
    }

    def "Should remove comment from book"() {
        given: "Should add book and comment"
        facade.addBook(TWILIGHT)
        facade.addCommentToBook(TWILIGHT_COMMENT_FIRST)

        when: "Should remove comment from book"
        facade.removeCommentById(TWILIGHT_COMMENT_FIRST.commentId)

        then: "Book should not have any comments"
        BookError response = facade.findCommentById(TWILIGHT_COMMENT_FIRST.commentId).getLeft()
        response == COMMENT_NOT_FOUND
    }

    def "Should remove two comments from book"() {
        given: "Should add book and comment"
        facade.addBook(TWILIGHT)
        facade.addCommentToBook(TWILIGHT_COMMENT_FIRST)
        facade.addCommentToBook(TWILIGHT_COMMENT_SECOND)

        when: "Should remove comments from book"
        facade.removeCommentById(TWILIGHT_COMMENT_FIRST.commentId)
        facade.removeCommentById(TWILIGHT_COMMENT_SECOND.commentId)

        then: "Book should not have any comments"
        BookError response_first_comment = facade.findCommentById(TWILIGHT_COMMENT_FIRST.commentId).getLeft()
        BookError response_second_comment = facade.findCommentById(TWILIGHT_COMMENT_SECOND.commentId).getLeft()
        response_first_comment == COMMENT_NOT_FOUND
        response_second_comment == COMMENT_NOT_FOUND

    }
}