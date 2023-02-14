package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class BookFacade {
    private final BookManager bookManager;

    public Validation<Seq<String>, BookCreateDto> validateBook(BookCreateDto bookCreateDto) {
        return BookValidation.validate(bookCreateDto);
    }

    public BookCreateDto addBook(BookCreateDto bookCreateDto) {
        return bookManager.addBook(bookCreateDto).response();
    }

    public Either<BookError, BookCreateDto> findBookById(Long bookId) {
        return bookManager.findBookById(bookId)
                .map(Book::response);
    }

    public List<BookCreateDto> fechAllBooks(int page) {
        return bookManager.fetchAllBooksWithPageable(page)
                .stream()
                .map(Book::response)
                .collect(Collectors.toList());
    }

    public void removeBookById(Long bookId) {
        bookManager.removeBookById(bookId);
    }

    public Either<BookError, CommentCreateDto> addCommentToBook(CommentCreateDto commentRequest) {
        return bookManager.addCommentToBook(commentRequest.getBookId(), commentRequest);
    }

    public Either<BookError, CommentCreateDto> findCommentById(Long commentId) {
        return bookManager.findCommentById(commentId)
                .map(Comment::response);
    }
}