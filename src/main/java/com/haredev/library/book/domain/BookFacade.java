package com.haredev.library.book.domain;

import com.haredev.library.book.controller.input.BookRequest;
import com.haredev.library.book.controller.output.BookResponse;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    public BookResponse addBook(BookRequest request) {
        Book book = bookCreator.from(request);
        return bookRepository.insert(book).response();
    }

    public Option<BookResponse> findBook(UUID bookid) {
        return bookRepository.findById(bookid).map(Book::response);
    }

    public List<BookResponse> fetchAllBooks() {
        return bookRepository.findAll().stream().map(Book::response).collect(Collectors.toList());
    }

    public void removeBook(UUID bookId) {
        bookRepository.deleteById(bookId);
    }
}
