package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.*;
import com.haredev.library.infrastructure.errors.validation.ValidationErrorsConsumer;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.haredev.library.book.domain.api.error.BookError.BOOK_NOT_FOUND;
import static com.haredev.library.book.domain.api.error.BookError.COMMENT_NOT_FOUND;

@RequiredArgsConstructor
public class BookFacade {
    private final BookValidation bookValidation;
    private final BookManager bookManager;

    public Either<ValidationErrorsConsumer, BookCreateDto> validateBook(BookCreateDto bookCreateDto) {
        if (bookValidation.validate(bookCreateDto).isValid()) {
            return Either.right(addBook(bookCreateDto));
        }
        Seq<String> errors = bookValidation.validate(bookCreateDto).getError();
        return Either.left(ValidationErrorsConsumer.errorsAsJson(errors));
    }

    public BookCreateDto addBook(BookCreateDto bookCreateDto) {
        return bookManager.addBook(bookCreateDto).response();
    }

    public Either<BookError, BookCreateDto> findBookById(Long bookId) {
        return bookManager.findBookById(bookId).map(Book::response).toEither(BOOK_NOT_FOUND);
    }

    public List<BookCreateDto> fetchAllBooks(int page) {
        return bookManager.fetchAllBooksWithPageable(page)
                .stream()
                .map(Book::response)
                .collect(Collectors.toList());
    }

    public void removeBookById(Long bookId) {
        bookManager.removeBookById(bookId);
    }

    public Either<BookError, CommentDto> addCommentToBook(CommentCreateDto commentRequest) {
        return bookManager.addCommentToBook(commentRequest);
    }

    public Either<BookError, CommentDto> findCommentById(Long commentId) {
        return bookManager.findCommentById(commentId).map(Comment::toDto).toEither(COMMENT_NOT_FOUND);
    }

    public Either<BookError, List<CommentDto>> getBookByIdWithComments(Long bookId) {
        return bookManager.findBookById(bookId)
                .toEither(BOOK_NOT_FOUND)
                .map(Book::getAllComments);
    }

    public void removeCommentById(Long commentId) {
        bookManager.removeCommentById(commentId);
    }
}