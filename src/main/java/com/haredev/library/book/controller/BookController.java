package com.haredev.library.book.controller;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.infrastructure.errors.ErrorsConsumer;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static io.vavr.API.*;
import static io.vavr.Patterns.$Invalid;
import static io.vavr.Patterns.$Valid;

@AllArgsConstructor
@RestController
class BookController {
    private final BookFacade bookFacade;
    private final BookValidation bookValidation;

    @PostMapping
    ResponseEntity<Either<ErrorsConsumer, BookResponse>> addBookToInventory(
            @RequestBody BookRequest request) {
        return Match(bookValidation.validate(request)).of(
                Case($Valid($()), ResponseEntity.ok(Either.right(bookFacade.addBook(request)))),
                Case($Invalid($()), errors -> ResponseEntity.badRequest().body(Either.left(ErrorsConsumer.of(errors))))
        );
    }
}
