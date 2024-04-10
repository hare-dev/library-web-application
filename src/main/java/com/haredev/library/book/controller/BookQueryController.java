package com.haredev.library.book.controller;

import com.haredev.library.book.query.BookQueryFacade;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
final class BookQueryController {
    private final BookQueryFacade bookQueryFacade;

    @GetMapping("/category/criminal")
    ResponseEntity<?> fetchCriminalCategoryBooks() {
        return ResponseResolver.resolve(bookQueryFacade.findAllByCriminalCategory());
    }

    @GetMapping("/cover/soft")
    ResponseEntity<?> fetchSoftCoverBooks() {
        return ResponseResolver.resolve(bookQueryFacade.findAllBySoftCover());
    }
}
