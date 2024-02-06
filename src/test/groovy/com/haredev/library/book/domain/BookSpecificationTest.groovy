package com.haredev.library.book.domain


import com.haredev.library.book.samples.SampleBooks
import com.haredev.library.book.samples.SampleComments
import spock.lang.Specification

import java.time.LocalDateTime

import static com.haredev.library.book.domain.api.error.BookError.*

class BookSpecificationTest extends Specification {
    def facade = new BookConfiguration().bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository())

    def "Should be empty"() {
        expect:
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add one book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "System has one book"
        def ERROR_RESPONSE = facade.findBookById(BOOK_ONE.id)get()

        then: "Return added book"
        ERROR_RESPONSE == BOOK_ONE
    }

    def "Should have two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "System has two books"
        def BOOK_ONE_ADDED = facade.findBookById(BOOK_ONE.id).get()
        def BOOK_TWO_ADDED = facade.findBookById(BOOK_TWO.id).get()

        then: "Return two added books"
        BOOK_ONE_ADDED == BOOK_ONE
        BOOK_TWO_ADDED == BOOK_TWO
    }

    def "Should return all books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "Return list of books"
        def ADDED_BOOKS = facade.fetchAllBooks(PAGE)

        then: "Check added books"
        ADDED_BOOKS.contains(BOOK_ONE)
        ADDED_BOOKS.contains(BOOK_TWO)
    }

    def "Should remove one book"() {
        given: "Add one book to system"
        facade.addBook(BOOK_ONE)

        when: "Remove one book"
        facade.removeBookById(BOOK_ONE.id)

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "Remove two books"
        facade.removeBookById(BOOK_TWO.id)
        facade.removeBookById(BOOK_ONE.id)

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        def COMMENT_ADDED_TO_BOOK_ONE = facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE).get()

        then: "Return comment added to book"
        def COMMENT_ONE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId)
        COMMENT_ADDED_TO_BOOK_ONE.description == COMMENT_ONE.get().description
    }

    def "Should not add comment to not existing book"() {
        when: "Add comment to book"
        def ERROR_RESPONSE = facade.addCommentToBook(COMMENT_FOR_NOT_EXIST_BOOK_ID).getLeft()

        then: "Book not found"
        ERROR_RESPONSE == BOOK_NOT_FOUND
    }

    def "Should not add comment to book because description is null"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        def ERROR_RESPONSE = facade.addCommentToBook(COMMENT_WITH_NULL_DESCRIPTION).getLeft()

        then: "Return error with null description"
        ERROR_RESPONSE == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book with empty description"() {
        given: "Add book to System"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        def ERROR_RESPONSE = facade.addCommentToBook(COMMENT_WITH_EMPTY_DESCRIPTION).getLeft()

        then: "Return error with null description"
        ERROR_RESPONSE == NULL_OR_EMPTY_DESCRIPTION
    }

    def "Should not add comment to book with null date added"() {
        given: "Add book"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        def ERROR_RESPONSE = facade.addCommentToBook(COMMENT_WITH_NULL_DATE_ADDED).getLeft()

        then: "Return error with null date added"
        ERROR_RESPONSE == NULL_DATE_ADDED
    }

    def "Should get one comment from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)

        when: "Find book and return size of list with comments"
        def COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.id).get()

        then: "Book has one comment"
        COMMENTS_SIZE.size() == 1
    }

    def "Should get two comments from book"() {
        given: "Add book and comments"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)
        facade.addCommentToBook(COMMENT_TWO_FOR_BOOK_ONE)

        when: "Find book and return size of list with comments"
        def COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.id).get()

        then: "Book has two comments"
        COMMENTS_SIZE.size() == 2
    }

    def "Should return empty list of comments from book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Find book and return list with comments"
        def COMMENTS = facade.getBookByIdWithComments(BOOK_ONE.id).get()

        then: "List with comments from book is empty"
        COMMENTS.isEmpty()
    }

    def "Should remove comment from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE_FOR_BOOK_ONE)

        when: "Remove comment from book"
        facade.removeCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId)

        then: "Book not have any comments"
        def ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId).getLeft()
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
        def COMMENT_ONE_ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE_FOR_BOOK_ONE.commentId).getLeft()
        def COMMENT_TWO_ERROR_RESPONSE = facade.findCommentById(COMMENT_TWO_FOR_BOOK_ONE.commentId).getLeft()
        COMMENT_ONE_ERROR_RESPONSE == COMMENT_NOT_FOUND
        COMMENT_TWO_ERROR_RESPONSE == COMMENT_NOT_FOUND
    }

    def final PAGE = 1
    def final BOOK_ONE = SampleBooks.createBookSample(0L, "Twilight", "Stephenie Meyer")
    def final BOOK_TWO = SampleBooks.createBookSample(1L, "Django", "Quentin Tarantino")

    def final COMMENT_ONE_FOR_BOOK_ONE = SampleComments.createCommentSample(0L, BOOK_ONE.id, "Best book!", LocalDateTime.now())
    def final COMMENT_TWO_FOR_BOOK_ONE = SampleComments.createCommentSample(1L, BOOK_ONE.id, "Fantastic book!", LocalDateTime.now())

    def final COMMENT_FOR_NOT_EXIST_BOOK_ID = SampleComments.createCommentSampleWithNotExistsBookId()
    def final COMMENT_WITH_NULL_DESCRIPTION = SampleComments.createCommentSampleWithNullDescription()
    def final COMMENT_WITH_EMPTY_DESCRIPTION = SampleComments.createCommentSampleWithEmptyDescription()
    def final COMMENT_WITH_NULL_DATE_ADDED = SampleComments.createCommentSampleWithNullDateAdded()
}