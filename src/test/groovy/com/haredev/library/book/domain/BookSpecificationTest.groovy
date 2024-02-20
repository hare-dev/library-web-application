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
        def RESULT = facade.findBookById(BOOK_ONE.id()).get()

        then: "Return added book"
        RESULT == BOOK_ONE
    }

    def "Should not find book with not exist id"() {
        when: "Find not exist book by id"
        def ERROR_RESPONSE = facade.findBookById(SampleBooks.notExistBookWithThisId).getLeft()

        then: "Return response with BOOK_NOT_FOUND"
        ERROR_RESPONSE == BOOK_NOT_FOUND
    }

    def "Should not add books with the same isbn code"() {
        given: "Add two books to system"
        def BOOK_ONE = SampleBooks.createBookSampleWithTheSameIsbn()
        def BOOK_TWO = SampleBooks.createBookSampleWithTheSameIsbn()

        when: "System has two books"
        facade.addBook(BOOK_ONE)
        def ERROR_RESPONSE = facade.addBook(BOOK_TWO).getLeft()

        then: "Return two added books"
        ERROR_RESPONSE == ISBN_DUPLICATED
    }

    def "Should have two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "System has two books"
        def BOOK_ONE_ADDED = facade.findBookById(BOOK_ONE.id()).get()
        def BOOK_TWO_ADDED = facade.findBookById(BOOK_TWO.id()).get()

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
        facade.removeBookById(BOOK_ONE.id())

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Add two books to system"
        facade.addBook(BOOK_ONE)
        facade.addBook(BOOK_TWO)

        when: "Remove two books"
        facade.removeBookById(BOOK_TWO.id())
        facade.removeBookById(BOOK_ONE.id())

        then: "System is empty"
        facade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Add comment to book"
        def RESULT = facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id()).get()

        then: "Return comment added to book"
        def COMMENT_ONE = facade.findCommentById(BOOK_ONE.id())
        RESULT.description() == COMMENT_ONE.get().description()
    }

    def "Should not add comment to not exist book"() {
        when: "Add comment to not exist book"
        def ERROR_RESPONSE = facade.addCommentToBook(COMMENT_ONE, SampleBooks.notExistBookWithThisId).getLeft()

        then: "Book not found"
        ERROR_RESPONSE == BOOK_NOT_FOUND
    }

    def "Should get one comment from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id())

        when: "Find book and return size of list with comments"
        def COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.id()).get()

        then: "Book has one comment"
        COMMENTS_SIZE.size() == 1
    }

    def "Should get two comments from book"() {
        given: "Add book and comments"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id())
        facade.addCommentToBook(COMMENT_TWO, BOOK_ONE.id())

        when: "Find book and return size of list with comments"
        def COMMENTS_SIZE = facade.getBookByIdWithComments(BOOK_ONE.id()).get()

        then: "Book has two comments"
        COMMENTS_SIZE.size() == 2
    }

    def "Should return empty list of comments from book"() {
        given: "Add book to system"
        facade.addBook(BOOK_ONE)

        when: "Find book and return list with comments"
        def COMMENTS = facade.getBookByIdWithComments(BOOK_ONE.id()).get()

        then: "List with comments from book is empty"
        COMMENTS.isEmpty()
    }

    def "Should remove comment from book"() {
        given: "Add book and comment"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id())

        when: "Remove comment from book"
        facade.removeCommentById(COMMENT_ONE.commentId())

        then: "Book not have any comments"
        def ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE.commentId()).getLeft()
        ERROR_RESPONSE == COMMENT_NOT_FOUND
    }

    def "Should remove two comments from book"() {
        given: "Add book and comments"
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id())
        facade.addCommentToBook(COMMENT_TWO, BOOK_ONE.id())

        when: "Remove comments from book"
        facade.removeCommentById(COMMENT_ONE.commentId())
        facade.removeCommentById(COMMENT_TWO.commentId())

        then: "Book should not have any comments"
        def COMMENT_ONE_ERROR_RESPONSE = facade.findCommentById(COMMENT_ONE.commentId()).getLeft()
        def COMMENT_TWO_ERROR_RESPONSE = facade.findCommentById(COMMENT_TWO.commentId()).getLeft()
        COMMENT_ONE_ERROR_RESPONSE == COMMENT_NOT_FOUND
        COMMENT_TWO_ERROR_RESPONSE == COMMENT_NOT_FOUND
    }

    def "Should update one book"() {
        given: "Add book to update"
        def BOOK = SampleBooks.createBookSampleToUpdate()
        def BOOK_UPDATE = SampleBooks.createBookWithDataToUpdateSample()
        facade.addBook(BOOK)

        when: "Update book"
        def UPDATE_RESULT = facade.updateBookById(BOOK.id(), BOOK_UPDATE).get()

        then: "Compare book input update with output update"
        UPDATE_RESULT.title() == BOOK_UPDATE.title()
        UPDATE_RESULT.author() == BOOK_UPDATE.author()
        UPDATE_RESULT.isbn() == BOOK_UPDATE.isbn()
        UPDATE_RESULT.description() == BOOK_UPDATE.description()
        UPDATE_RESULT.yearPublication() == BOOK_UPDATE.yearPublication()
        UPDATE_RESULT.bookCover() == BOOK_UPDATE.bookCover()
    }

    def "Should not update not exist book"() {
        when: "Update not exist book"
        def BOOK_UPDATE = SampleBooks.createBookWithDataToUpdateSample()
        def ERROR_RESPONSE = facade.updateBookById(SampleBooks.notExistBookWithThisId, BOOK_UPDATE).getLeft()

        then: "Return error with book not found"
        ERROR_RESPONSE == BOOK_NOT_FOUND
    }

    def "Should update one comment"() {
        given: "Add comment to book"
        def COMMENT_UPDATE = SampleComments.createCommentWithDataToUpdateSample()
        facade.addBook(BOOK_ONE)
        facade.addCommentToBook(COMMENT_ONE, BOOK_ONE.id())

        when: "Update comment"
        def UPDATE_RESULT = facade.updateCommentById(COMMENT_ONE.commentId(), COMMENT_UPDATE).get()

        then: "Compare comment input update with output update"
        UPDATE_RESULT.description() == COMMENT_UPDATE.description()
    }

    def "Should not update not exist comment"() {
        when: "Update not exist book"
        def COMMENT_UPDATE = SampleComments.createCommentWithDataToUpdateSample()
        def ERROR_RESPONSE = facade.updateCommentById(SampleComments.notExistCommentWithThisId, COMMENT_UPDATE).getLeft()

        then: "Return error with book not found"
        ERROR_RESPONSE == COMMENT_NOT_FOUND
    }

    def final PAGE = 1
    def final BOOK_ONE = SampleBooks.createBookSampleToUpdate(0L, "Twilight", "Stephenie Meyer", "0-596-52068-9")
    def final BOOK_TWO = SampleBooks.createBookSampleToUpdate(1L, "Django", "Quentin Tarantino", "978 0 596 52068 7")

    def final COMMENT_ONE = SampleComments.createCommentSample(0L, "Best book!", LocalDateTime.now())
    def final COMMENT_TWO = SampleComments.createCommentSample(1L,"Fantastic book!", LocalDateTime.now())
}