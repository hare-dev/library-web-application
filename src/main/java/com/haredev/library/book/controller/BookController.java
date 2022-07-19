package com.haredev.library.book.controller;

import com.haredev.library.book.dto.BookCreateDto;
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
import java.util.function.Supplier;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@AllArgsConstructor
@RestController
final class BookController {
    private final BookFacade bookFacade;
    private final BookValidation bookValidation;

    @PostMapping
    ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> addBookToInventory(
            @RequestBody BookCreateDto bookCreateDto) {
        return Match(bookValidation.validate(bookCreateDto)).of(
                Case($Invalid($()), this::invalid),
                Case($Valid($()), valid(bookCreateDto))
        );
    }

    private ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>> invalid(Seq<String> errors) {
        return ResponseEntity.badRequest().body(
                Either.left(ValidationErrorsConsumer.errorsAsJson(errors)));
    }

    private Supplier<ResponseEntity<Either<ValidationErrorsConsumer, BookCreateDto>>> valid(BookCreateDto bookCreateDto) {
        return () -> ResponseEntity.ok(Either.right(bookFacade.addBook(bookCreateDto)));
    }

    @GetMapping("book/{id}")
    ResponseEntity findBookByIdFromInventory(@RequestParam String bookId) {
        Option<BookCreateDto> response = bookFacade.findBook(bookId);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("books")
    List<BookCreateDto> fetchAllBooksFromInventory() {
       return bookFacade.fetchAllBooks();
    }

    @DeleteMapping("book/{id}")
    void removeBookFromInventory(@RequestParam String bookId) {
        bookFacade.removeBook(bookId);
    }
}
