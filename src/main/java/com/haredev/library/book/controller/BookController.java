package com.haredev.library.book.controller;

import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.*;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import io.vavr.control.Either;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping("/books/add")
    ResponseEntity<?> addBook(@Valid @RequestBody final BookCreateDto request) {
        Either<BookError, BookCreateDto> response = bookFacade.addBook(request);
        return ResponseResolver.resolve(response);
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
    ResponseEntity<?> removeBook(@PathVariable final Long bookId) {
        bookFacade.removeBookById(bookId);
        return ResponseEntity.noContent().build();
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
    ResponseEntity<?> removeCommentById(@PathVariable final Long commentId) {
        bookFacade.removeCommentById(commentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{bookId}")
    ResponseEntity<?> updateBook(@PathVariable final Long bookId, @RequestBody final BookUpdateDto toUpdate) {
        Either<BookError, BookCreateDto> response = bookFacade.updateBookById(bookId, toUpdate);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/books/comments/{commentId}")
    ResponseEntity<?> updateComment(@PathVariable final Long commentId, @RequestBody final CommentUpdateDto toUpdate) {
        Either<BookError, CommentDto> response = bookFacade.updateCommentById(commentId, toUpdate);
        return ResponseResolver.resolve(response);
    }
}
