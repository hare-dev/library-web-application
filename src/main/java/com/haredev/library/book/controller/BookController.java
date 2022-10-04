package com.haredev.library.book.controller;

import com.haredev.library.book.dto.BookCreateDto;
import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.infrastructure.errors.ValidationErrorsConsumer;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@AllArgsConstructor
@RestController
@RequestMapping("books")
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping("/add")
    ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> addBook(
            @RequestBody BookCreateDto bookCreateDto) {
        return Match(bookFacade.validateBook(bookCreateDto)).of(
                Case($Invalid($()), this::invalid),
                Case($Valid($()), valid(bookCreateDto))
        );
    }

    private ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> invalid(Seq<String> errors) {
        return ResponseEntity.badRequest().body(
                Either.left(ValidationErrorsConsumer.errorsAsJson(errors)));
    }

    private Supplier<ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>>> valid(BookCreateDto book) {
        return () -> new ResponseEntity<>(Either.right(bookFacade.addBook(book)), HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    ResponseEntity findBookById(@PathVariable Long bookId) {
        Option<BookCreateDto> response = bookFacade.findBookById(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/books")
    List<BookCreateDto> fetchAllBooks(@RequestParam(required = false) Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        return bookFacade.fetchAllBooks(pageNumber);
    }

    @DeleteMapping("/{bookId}")
    ResponseEntity<Void> removeBook(@PathVariable Long bookId) {
        bookFacade.removeBookById(bookId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
