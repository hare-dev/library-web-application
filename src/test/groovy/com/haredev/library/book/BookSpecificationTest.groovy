package com.haredev.library.book

import com.haredev.library.book.samples.SampleBooks
import com.haredev.library.book.samples.SampleComments
import spock.lang.Specification

import static com.haredev.library.book.domain.api.error.BookError.*

class BookSpecificationTest extends Specification implements SampleBooks, SampleComments {
    final def bookFacade = BookTestConfiguration.getConfiguration()
    final def PAGE = 1

    def "Should be empty"() {
        expect:
        bookFacade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add one book"() {
        given: "Add book to system"
        bookFacade.addBook(twilight)

        when: "System has one book"
        def result = bookFacade.findBookById(twilight.id()).get()

        then: "Return added book"
        result == twilight
    }

    def "Should not find book with not exist id"() {
        when: "Find not exist book by id"
        def error = bookFacade.findBookById(bookWithThisIdNotExist).getLeft()

        then: "Return response with BOOK_NOT_FOUND"
        error == BOOK_NOT_FOUND
    }

    def "Should not add books with the same isbn code"() {
        given: "Add first book to system"
        def twilight = createBookSampleWithTheSameIsbn(0L)
        def jumanji = createBookSampleWithTheSameIsbn(1L)
        bookFacade.addBook(twilight)

        when: "System try to add jumanji book with the same isbn like added twilight book"
        def error = bookFacade.addBook(jumanji).getLeft()

        then: "Return isbn is duplicated"
        error == ISBN_DUPLICATED
    }

    def "Should has two books"() {
        when: "Add two books to system"
        bookFacade.addBook(twilight)
        bookFacade.addBook(jumanji)

        then: "Return two added books"
        twilight == bookFacade.findBookById(twilight.id()).get()
        jumanji == bookFacade.findBookById(jumanji.id()).get()
    }

    def "Should return all books"() {
        given: "Add two books to system"
        bookFacade.addBook(twilight)
        bookFacade.addBook(jumanji)

        when: "Return list of books"
        def booksCollection = bookFacade.fetchAllBooks(PAGE)

        then: "Check added books"
        booksCollection.contains(twilight)
        booksCollection.contains(jumanji)
    }

    def "Should remove one book"() {
        given: "Add one book to system"
        bookFacade.addBook(twilight)

        when: "Remove one book"
        bookFacade.removeBookById(twilight.id())

        then: "System is empty"
        bookFacade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should remove two books"() {
        given: "Add two books to system"
        bookFacade.addBook(twilight)
        bookFacade.addBook(jumanji)

        when: "Remove two books"
        bookFacade.removeBookById(twilight.id())
        bookFacade.removeBookById(jumanji.id())

        then: "System is empty"
        bookFacade.fetchAllBooks(PAGE).isEmpty()
    }

    def "Should add comment to book"() {
        given: "Add book to system"
        bookFacade.addBook(twilight)

        when: "Add comment to book"
        def twilight_comment = bookFacade.addCommentToBook(comment_best_book, twilight.id()).get()
        def twilight_comment_found = bookFacade.findCommentById(comment_best_book.id()).get()

        then: "Should "
        twilight_comment == twilight_comment_found
    }

    def "Should not add comment to not exist book"() {
        when: "Add comment to not exist book"
        def error = bookFacade.addCommentToBook(comment_best_book, bookWithThisIdNotExist).getLeft()

        then: "Book not found"
        error == BOOK_NOT_FOUND
    }

    def "Should get one comment from book"() {
        given: "Add book and comment"
        bookFacade.addBook(twilight)
        bookFacade.addCommentToBook(comment_best_book, twilight.id())

        when: "Find book and return size of list with comments"
        def comments_collection_size = bookFacade.fetchCommentsByBookId(twilight.id()).get().size()

        then: "Book has one comment"
        comments_collection_size == 1
    }

    def "Should get two comments from book"() {
        given: "Add book and comments"
        bookFacade.addBook(twilight)
        bookFacade.addCommentToBook(comment_best_book, twilight.id())
        bookFacade.addCommentToBook(comment_amazing_adventure, twilight.id())

        when: "Find book and return size of list with comments"
        def comments_collection_size = bookFacade.fetchCommentsByBookId(twilight.id()).get().size()

        then: "Book has two comments"
        comments_collection_size == 2
    }

    def "Should return empty list of comments from book"() {
        given: "Add book to system"
        bookFacade.addBook(twilight)

        when: "Find book and return list with comments"
        def comments_collection = bookFacade.fetchCommentsByBookId(twilight.id()).get()

        then: "Comments collection is empty"
        comments_collection.isEmpty()
    }

    def "Should remove comment from book"() {
        given: "Add comment to book"
        bookFacade.addBook(twilight)
        bookFacade.addCommentToBook(comment_best_book, twilight.id())

        when: "Remove comment from book"
        bookFacade.removeCommentById(comment_best_book.id())
        def error = bookFacade.findCommentById(comment_best_book.id()).getLeft()

        then: "Not found comment for book"
        error == COMMENT_NOT_FOUND
    }

    def "Should update one book"() {
        given: "Add book to update"
        bookFacade.addBook(twilight)
        def data_to_update_twilight = createBookSampleWithDataToUpdate()

        when: "Update data for book"
        def twilight_update_result = bookFacade.updateBookById(twilight.id(), data_to_update_twilight).get()
        def twilight_founded = bookFacade.findBookById(twilight.id()).get()

        then: "Compare book input update with founded book after update"
        twilight_founded == twilight_update_result
    }

    def "Should not update not exist book"() {
        when: "Update not exist book"
        def data_to_update_twilight = createBookSampleWithDataToUpdate()
        def error = bookFacade.updateBookById(bookWithThisIdNotExist, data_to_update_twilight).getLeft()

        then: "Return error with book not found"
        error == BOOK_NOT_FOUND
    }

    def "Should update one comment"() {
        given: "Add comment to book"
        def data_to_update_comment = createCommentSampleWitDataToUpdate()
        bookFacade.addBook(twilight)
        bookFacade.addCommentToBook(comment_best_book, twilight.id())

        when: "Update comment"
        def comment_update_result = bookFacade.updateCommentById(comment_best_book.id(), data_to_update_comment).get()
        def comment_founded = bookFacade.findCommentById(comment_best_book.id()).get()

        then: "Compare comment input update with founded comment after update"
        comment_update_result == comment_founded
    }

    def "Should not update not exist comment"() {
        when: "Update not exist book"
        def data_to_update_comment = createCommentSampleWitDataToUpdate()
        def error = bookFacade.updateCommentById(commentWithThisIdNotExist, data_to_update_comment).getLeft()

        then: "Return book not found"
        error == COMMENT_NOT_FOUND
    }
}