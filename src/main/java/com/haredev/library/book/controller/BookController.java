package com.haredev.library.book.controller;

import com.haredev.library.book.domain.BookFacade;
import com.haredev.library.book.domain.dto.BookCreateDto;
import com.haredev.library.book.domain.dto.CommentCreateDto;
import com.haredev.library.book.domain.dto.update.BookUpdateDto;
import com.haredev.library.book.domain.dto.update.CommentUpdateDto;
import com.haredev.library.infrastructure.errors.ResponseResolver;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
final class BookController {
    private final BookFacade bookFacade;

    @PostMapping
    ResponseEntity<?> addBook(@Valid @RequestBody final BookCreateDto request) {
        var response = bookFacade.addBook(request);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<?> findBookById(@PathVariable final Long id) {
        var response = bookFacade.findBookById(id);
        return ResponseResolver.resolve(response);
    }

    @GetMapping
    ResponseEntity<?> fetchAllBooks(@RequestParam(required = false) final Integer page) {
        int pageNumber = page != null && page >= 0 ? page : 0;
        var response = bookFacade.fetchAllBooks(pageNumber);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> removeBook(@PathVariable final Long id) {
        bookFacade.removeBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    ResponseEntity<?> addCommentToBook(@RequestBody @Valid final CommentCreateDto request, @PathVariable Long id) {
        var response = bookFacade.addCommentToBook(request, id);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/comments/{id}")
    ResponseEntity<?> findCommentById(@PathVariable final Long id) {
        var response = bookFacade.findCommentById(id);
        return ResponseResolver.resolve(response);
    }

    @GetMapping("/{id}/comments")
    ResponseEntity<?> findCommentsByBookId(@PathVariable final Long id) {
        var response = bookFacade.fetchCommentsByBookId(id);
        return ResponseResolver.resolve(response);
    }

    @DeleteMapping("/comments/{id}")
    ResponseEntity<?> removeCommentById(@PathVariable final Long id) {
        bookFacade.removeCommentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateBook(@PathVariable final Long id, @RequestBody @Valid final BookUpdateDto toUpdate) {
        var response = bookFacade.updateBookById(id, toUpdate);
        return ResponseResolver.resolve(response);
    }

    @PutMapping("/comments/{id}")
    ResponseEntity<?> updateComment(@PathVariable final Long id, @RequestBody final @Valid CommentUpdateDto toUpdate) {
        var response = bookFacade.updateCommentById(id, toUpdate);
        return ResponseResolver.resolve(response);
    }
}
