package com.haredev.library.book.controller;

import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.CommentDto;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.infrastructure.errors.validation.ValidationErrorsConsumer;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@RestController
@RequiredArgsConstructor
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping("/books/add")
    ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> addBook(
            @RequestBody BookCreateDto request) {
        return Match(bookFacade.validateBook(request)).of(
                Case($Invalid($()), this::invalid),
                Case($Valid($()), valid(request))
        );
    }

    private ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> invalid(Seq<String> errors) {
        return ResponseEntity.badRequest().body(
                Either.left(ValidationErrorsConsumer.errorsAsJson(errors)));
    }

    private ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> valid(BookCreateDto book) {
        return new ResponseEntity<>(Either.right(bookFacade.addBook(book)), HttpStatus.CREATED);
    }

    @GetMapping("/books/{bookId}")
    ResponseEntity findBookById(@PathVariable Long bookId) {
        Either<BookError, BookCreateDto> response = bookFacade.findBookById(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books")
    ResponseEntity fetchAllBooks(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<BookCreateDto> response = bookFacade.fetchAllBooks(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/books/{bookId}")
    HttpStatus removeBook(@PathVariable Long bookId) {
        bookFacade.removeBookById(bookId);
        return HttpStatus.OK;
    }

    @PostMapping("/books/{bookId}")
    ResponseEntity addCommentToBook(@RequestBody CommentCreateDto commentRequest) {
        Either<BookError, CommentDto> response = bookFacade.addCommentToBook(commentRequest);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/comments/{commentId}")
    ResponseEntity findCommentById(@PathVariable Long commentId) {
        Either<BookError, CommentDto> response = bookFacade.findCommentById(commentId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books/{bookId}/comments")
    ResponseEntity getCommentsFromBook(@PathVariable Long bookId) {
        Either<BookError, List<CommentDto>> response = bookFacade.getBookByIdWithComments(bookId);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("comments/{commentId}")
    HttpStatus removeCommentById(@PathVariable Long commentId) {
        bookFacade.removeCommentById(commentId);
        return HttpStatus.OK;
    }
}
