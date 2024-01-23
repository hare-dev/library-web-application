package com.haredev.library.book.controller;

import com.haredev.library.book.query.BookQueryFacade;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
final class BookQueryController {
    private final BookQueryFacade bookQueryFacade;

    @GetMapping("/books/category/criminal")
    ResponseEntity<?> bookFromCriminalCategory() {
        return ResponseResolver.resolve(bookQueryFacade.findAllByCriminalCategory());
    }

    @GetMapping("/books/cover/soft")
    ResponseEntity<?> bookWithSoftCover() {
        return ResponseResolver.resolve(bookQueryFacade.findAllBySoftCover());
    }
}
