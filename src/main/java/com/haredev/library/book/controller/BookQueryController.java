package com.haredev.library.book.controller;

import com.haredev.library.book.query.BookQueryDto;
import com.haredev.library.book.query.BookQueryFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
final class BookQueryController {
    private final BookQueryFacade bookQueryFacade;

    @GetMapping("/books/")
    List<BookQueryDto> bookFromCriminalCategory() {
        return bookQueryFacade.findAllByCriminalCategory();
    }

    @GetMapping("/books/")
    List<BookQueryDto> bookWithSoftCover() {
        return bookQueryFacade.findAllBySoftCover();
    }
}
