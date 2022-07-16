package com.haredev.library.book.controller;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import com.haredev.library.infrastructure.errors.ValidationErrorsConsumer;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static com.haredev.library.infrastructure.errors.ValidationErrorsConsumer.errorsAsJson;
import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@AllArgsConstructor
@RestController
class BookController {
    private final BookFacade bookFacade;
    private final BookValidation bookValidation;

    @PostMapping
    ResponseEntity<Either<ValidationErrorsConsumer, BookResponse>> addBookToInventory(
            @RequestBody BookRequest request) {
        return Match(bookValidation.validate(request)).of(
                Case($Valid($()), valid(request)),
                Case($Invalid($()), invalid())
        );
    }

    @GetMapping("book/{id}")
    ResponseEntity findBookById(@RequestParam UUID bookId) {
        Option<BookResponse> response = bookFacade.findBook(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("books")
    List<BookResponse> fetchAllBooksFromInventory() {
       return bookFacade.fetchAllBooks();
    }

    @DeleteMapping("book/{id}")
    void removeBookFromSystem(@RequestParam UUID bookId) {
        bookFacade.removeBook(bookId);
    }

    private Function<Seq<String>, ResponseEntity<Either<ValidationErrorsConsumer, BookResponse>>> invalid() {
        return errors -> ResponseEntity.badRequest().body(Either.left(errorsAsJson((errors))));
    }

    private ResponseEntity<Either<ValidationErrorsConsumer, BookResponse>> valid(BookRequest request) {
        return ResponseEntity.ok(Either.right(bookFacade.addBook(request)));
    }
}
