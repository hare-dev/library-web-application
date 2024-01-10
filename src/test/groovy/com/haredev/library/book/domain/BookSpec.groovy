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

    def final PAGE = 1
    def final BOOK_ONE = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final BOOK_TWO = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final COMMENT_FOR_NOT_EXISTING_BOOK = SampleComments.createCommentSample(3L, 111, "NOT EXISTING", LocalDateTime.now())
    def final COMMENT_ONE_FOR_BOOK_ONE = SampleComments.createCommentSample(0L, BOOK_ONE.bookId, "Best book!", LocalDateTime.now())
    def final COMMENT_TWO_FOR_BOOK_ONE = SampleComments.createCommentSample(1L, BOOK_ONE.bookId, "Fantastic book!", LocalDateTime.now())
    def final COMMENT_FOR_BOOK_ONE_WITH_NULL_DESCRIPTION = SampleComments.createCommentSample(0L, BOOK_ONE.bookId, null, LocalDateTime.now())
    def final COMMENT_FOR_BOOK_ONE_WITH_EMPTY_DESCRIPTION = SampleComments.createCommentSample(0L, BOOK_ONE.bookId, "", LocalDateTime.now())
    def final COMMENT_FOR_BOOK_ONE_WITH_NULL_DATE_ADDED = SampleComments.createCommentSample(0L, BOOK_ONE.bookId, "Best book!", null)

    def "Should be empty"() {
        expect:
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add one book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "System has one book"
        Either<BookError, BookCreateDto> ERROR_RESPONSE = facade.findBookById(BOOK_ONE.bookId)

        then: "Return added book"
        ERROR_RESPONSE.get() == BOOK_ONE
    }

    def "Should have two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "System has two books"
        Either<BookError, BookCreateDto> BOOK_ONE_ADDED = facade.findBookById(BOOK_ONE.bookId)
        Either<BookError, BookCreateDto> BOOK_TWO_ADDED = facade.findBookById(BOOK_TWO.bookId)

        then: "Return two added books"
        BOOK_ONE_ADDED.get() == BOOK_ONE
        BOOK_TWO_ADDED.get() == BOOK_TWO
    }

    def "Should return all books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "Return list of books"
        List<BookCreateDto> ADDED_BOOKS = facade.fetchAllBooks(PAGE)

        then: "Check added books"
        ADDED_BOOKS.contains(BOOK_ONE)
        ADDED_BOOKS.contains(BOOK_TWO)
    }

    def "Should remove one book"() {
        given: "Add one book to system"
        facade.addBook(BOOK_ONE)

        when: "Remove one book"
        facade.removeBookById(BOOK_ONE.bookId)

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "Remove two books"
        facade.removeBookById(BOOK_TWO.bookId)
        facade.removeBookById(BOOK_ONE.bookId)

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        Either<BookError, CommentDto> BOOK_ONE_COMMENT_ONE_RESPONSE = facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)

        then: "Return comment added to book"
        Either<BookError, CommentDto> COMMENT_ONE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId)
        BOOK_ONE_COMMENT_ONE_RESPONSE.get().description == COMMENT_ONE.get().description
    }

    def "Should not add comment to not existing book"() {
        when: "Add comment to book"
        BookError ERROR_RESPONSE = facade.addCommentToBook(COMMENT_FOR_NOT_EXISTING_BOOK).getLeft()

        then: "Book not found"
        ERROR_RESPONSE == BOOK_NOT_FOUND
    }

    def "Should not add comment to book because description is null"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        BookError ERROR_RESPONSE = facade.addCommentToBook(COMMENT_FOR_BOOK_ONE_WITH_NULL_DESCRIPTION).getLeft()

        then: "Return error with null description"
        ERROR_RESPONSE == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because description is empty"() {
        given: "Add book to System"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        BookError ERROR_RESPONSE = facade.addCommentToBook(COMMENT_FOR_BOOK_ONE_WITH_EMPTY_DESCRIPTION).getLeft()

        then: "Return error with null description"
        ERROR_RESPONSE == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book because date added is null"() {
        given: "Add book"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        BookError ERROR_RESPONSE = facade.addCommentToBook(COMMENT_FOR_BOOK_ONE_WITH_NULL_DATE_ADDED).getLeft()

        then: "Return error with null date added"
        ERROR_RESPONSE == NULL_DATE_ADDED
    }

    def "Should get one comments from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)

        when: "Find book and return size of list with comments"
        Either<BookError, List<CommentDto>> COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.bookId)

        then: "Book has one comment"
        COMMENTS_SIZE.get().size() == 1
    }

    def "Should get two comments from book"() {
        given: "Add book and comments"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)
        facade.addCommentToBook(COMMENT_TWO_FOR_BOOK_ONE)

        when: "Find book and return size of list with comments"
        Either<BookError, List<CommentDto>> COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.bookId)

        then: "Book has two comments"
        COMMENTS_SIZE.get().size() == 2
    }

    def "Should remove comment from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)

        when: "Remove comment from book"
        facade.removeCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId)

        then: "Book not have any comments"
        BookError ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId).getLeft()
        ERROR_RESPONSE == COMMENT_NOT_FOUND
    }

    def "Should remove two comments from book"() {
        given: "Add book and comments"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)
        facade.addCommentToBook(COMMENT_TWO_FOR_BOOK_ONE)

        when: "Remove comments from book"
        facade.removeCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId)
        facade.removeCommentById(COMMENT_TWO_FOR_BOOK_ONE.commentId)

        then: "Book should not have any comments"
        BookError COMMENT_ONE_ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId).getLeft()
        BookError COMMENT_TWO_ERROR_RESPONSE = facade.findCommentById(COMMENT_TWO_FOR_BOOK_ONE.commentId).getLeft()
        COMMENT_ONE_ERROR_RESPONSE == COMMENT_NOT_FOUND
        COMMENT_TWO_ERROR_RESPONSE == COMMENT_NOT_FOUND
    }
}