package com.haredev.library.book.domain

import com.haredev.library.book.domain.api.error.BookError
import com.haredev.library.book.domain.dto.BookCreateDto
import com.haredev.library.book.domain.dto.CommentDto
import com.haredev.library.book.samples.SampleBooks
import com.haredev.library.book.samples.SampleComments
import io.vavr.control.Either
import io.vavr.control.Option
import spock.lang.Specification

import java.time.LocalDateTime

import static com.haredev.library.book.domain.api.error.BookError.*

class BookSpec extends Specification {
    def facade = new BookConfiguration().bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository())

    def final PAGE = 5
    def final twilight = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final django = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final NOT_EXISTING_BOOK = SampleComments.createCommentSample(3L, 99999L, "NOT EXISTING", LocalDateTime.now())
    def final twilightComment = SampleComments.createCommentSample(0L, twilight.bookId, "Best book!", LocalDateTime.now())
    def final twilightComment2 = SampleComments.createCommentSample(1L, twilight.bookId, "Fantastic book!", LocalDateTime.now())
    def final twilightCommentWithNullDescription = SampleComments.createCommentSample(0L, twilight.bookId, null, LocalDateTime.now())
    def final twilightCommentWithEmptyDescription = SampleComments.createCommentSample(0L, twilight.bookId, "", LocalDateTime.now())
    def final twilightCommentWithNullDateAdded = SampleComments.createCommentSample(0L, twilight.bookId, "Best book!", null)

    def "Should be empty"() {
        expect:
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add one book"() {
        given: "Should add book to system"
        facade.addBook(twilight)

        when: "Should have one book"
        Option<BookCreateDto> result = facade.findBookById(twilight.bookId)

        then: "Should return the book"
        result.get() == twilight
    }

    def "Should have two books"() {
        given: "Should add two books to system"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should have two books"
        Option<BookCreateDto> twilight_result = facade.findBookById(twilight.bookId)
        Option<BookCreateDto> django_result = facade.findBookById(django.bookId)

        then: "Should return two books"
        twilight_result.get() == twilight
        django_result.get() == django
    }

    def "Should return all books"() {
        given: "Should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should return list of books"
        List<BookCreateDto> foundBooks = facade.fetchAllBooks(PAGE)

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
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Should add two books"
        facade.addBook(twilight)
        facade.addBook(django)

        when: "Should remove two books"
        facade.removeBookById(django.bookId)
        facade.removeBookById(twilight.bookId)

        then: "Should be empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentDto> twilightCommentAdded = facade.addCommentToBook(twilightComment)

        then: "Should return comment added to book"
        Option<CommentDto> twilightCommentFounded = facade.findCommentById(twilightComment.commentId)
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
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(twilightCommentWithNullDescription)

        then: "Should return error with null description"
        response.getLeft() == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because description is empty"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(twilightCommentWithEmptyDescription)

        then: "Should return error with null description"
        response.getLeft() == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because date added is null"() {
        given: "Should add book"
        facade.addBook(twilight)

        when: "Should add comment to book"
        Either<BookError, CommentDto> response = facade.addCommentToBook(twilightCommentWithNullDateAdded)

        then: "Should return error with null description"
        response.getLeft() == NULL_DATE_ADDED
    }

    def "Should get one comments from book"() {
        given: "Should add book and comment"
        facade.addBook(twilight)
        facade.addCommentToBook(twilightComment)

        when: "Should find book and return list with comments"
        List<CommentDto> comments = facade.getCommentsFromBook(twilight.bookId)

        then: "Should be one comment"
        comments.size() == 1
    }

    def "Should get two comments from book"() {
        given: "Should add book and comments"
        facade.addBook(twilight)
        facade.addCommentToBook(twilightComment)
        facade.addCommentToBook(twilightComment2)

        when: "Should find book and return list with comments"
        List<CommentDto> comments = facade.getCommentsFromBook(twilight.bookId)

        then: "Book should have two comments"
        comments.size() == 2
    }

    def "Should remove comment from book"() {
        given: "Should add book and comment"
        facade.addBook(twilight)
        facade.addCommentToBook(twilightComment)

        when: "Should remove comment from book"
        facade.removeCommentFromBook(twilightComment.commentId)

        then: "Book should not have any comments"
        BookError error = facade.findCommentById(twilightComment.commentId).getLeft()
        error == COMMENT_NOT_FOUND
    }

    def "Should remove two comments from book"() {
        given: "Should add book and comment"
        facade.addBook(twilight)
        facade.addCommentToBook(twilightComment)
        facade.addCommentToBook(twilightComment2)

        when: "Should remove comments from book"
        facade.removeCommentFromBook(twilightComment.commentId)
        facade.removeCommentFromBook(twilightComment2.commentId)

        then: "Book should not have any comments"
        BookError error1 = facade.findCommentById(twilightComment.commentId).getLeft()
        BookError error2 = facade.findCommentById(twilightComment2.commentId).getLeft()
        error1 == COMMENT_NOT_FOUND
        error2 == COMMENT_NOT_FOUND
    }
}