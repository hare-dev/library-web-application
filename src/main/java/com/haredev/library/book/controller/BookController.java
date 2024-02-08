package com.haredev.library.book.controller;

import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.*;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.infrastructure.errors.validation.ValidationErrorsConsumer;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping("/books/add")
    Either<ValidationErrorsConsumer, BookCreateDto> addBook(
            @RequestBody final BookCreateDto request) {
        return bookFacade.validateBook(request);
    }

    @GetMapping("/books/{bookId}")
    ResponseEntity<?> findBookById(@PathVariable final Long bookId) {
        Either<BookError, BookCreateDto> response = bookFacade.findBookById(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books")
    ResponseEntity<?> fetchAllBooks(@RequestParam(required = false) final Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<BookCreateDto> response = bookFacade.fetchAllBooks(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/books/{bookId}")
    HttpStatus removeBook(@PathVariable final Long bookId) {
        bookFacade.removeBookById(bookId);
        return HttpStatus.OK;
    }

    @PostMapping("/books/comments/add")
    ResponseEntity<?> addCommentToBook(@RequestBody final CommentCreateDto request) {
        Either<BookError, CommentDto> response = bookFacade.addCommentToBook(request);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books/comments/{commentId}")
    ResponseEntity<?> findCommentById(@PathVariable final Long commentId) {
        Either<BookError, CommentDto> response = bookFacade.findCommentById(commentId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books/{bookId}/comments")
    ResponseEntity<?> getCommentsFromBook(@PathVariable final Long bookId) {
        Either<BookError, List<CommentDto>> response = bookFacade.getBookByIdWithComments(bookId);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/books/comments/{commentId}")
    HttpStatus removeCommentById(@PathVariable final Long commentId) {
        bookFacade.removeCommentById(commentId);
        return HttpStatus.NO_CONTENT;
    }

    @PutMapping("/books/{bookId}")
    ResponseEntity<?> updateBook(@PathVariable final Long bookId, @RequestBody final BookUpdateDto toUpdate) {
        Either<BookError, BookCreateDto> response = bookFacade.updateBook(bookId, toUpdate);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/books/comments/{commentId}")
    ResponseEntity<?> updateComment(@PathVariable final Long commentId, @RequestBody final CommentUpdateDto toUpdate) {
        Either<BookError, CommentDto> response = bookFacade.updateComment(commentId, toUpdate);
        return ResponseResolver.resolve(response);
    }
}
