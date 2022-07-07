package com.haredev.library.book.controller;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.book.controller.validation.ErrorsConsumer;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import io.vavr.control.Either;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.haredev.library.book.controller.validation.ErrorsConsumer.*;
import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@AllArgsConstructor
@RestController
@RequestMapping("books")
class BookController {
    private final BookFacade bookFacade;
    private final BookValidation bookValidation;

    @PostMapping
    ResponseEntity<Either<ErrorsConsumer, BookResponse>> addBookToInventory(
            @RequestBody BookRequest request) {
        return Match(bookValidation.validate(request)).of(
                Case($Valid($()), ResponseEntity.ok(Either.right(bookFacade.addBook(request)))),
                Case($Invalid($()), errors -> ResponseEntity.badRequest().body(Either.left(of((errors)))))
        );
    }

    @GetMapping
    ResponseEntity<BookResponse> findBookById(@PathVariable UUID bookId) {
        Option<BookResponse> response = bookFacade.findBook(bookId);
        return ResponseResolver.resolve(response);
    }
}
