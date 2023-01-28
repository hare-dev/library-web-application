package com.haredev.library.book.controller;

import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.infrastructure.errors.validation.ValidationErrorsConsumer;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@RestController
@RequiredArgsConstructor
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping("books/add")
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

    private Supplier<ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>>> valid(BookCreateDto book) {
        return () -> new ResponseEntity<>(Either.right(bookFacade.addBook(book)), HttpStatus.CREATED);
    }

    @GetMapping("books/{bookId}")
    ResponseEntity findBookById(@PathVariable Long bookId) {
        Either<BookError, BookCreateDto> response = bookFacade.findBookById(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books")
    ResponseEntity fetchAllBooksWithPageable(@RequestParam(required = false) Integer page,
                                         @RequestParam int pageSize) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        List<BookCreateDto> response = bookFacade.fechAllBooksWithPageable(pageNumber, pageSize);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books")
    ResponseEntity fetchAllBooks() {
        List<BookCreateDto> response = bookFacade.fetchAllBooks();
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("books/{bookId}")
    HttpStatus removeBook(@PathVariable Long bookId) {
        bookFacade.removeBookById(bookId);
        return HttpStatus.OK;
    }
}
