package com.haredev.library.book.domain;

import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    public BookCreateDto addBook(BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.insert(book).response();
    }

    public Option<BookCreateDto> findBook(String bookid) {
        return bookRepository.findById(bookid).map(Book::response);
    }

    public List<BookCreateDto> fetchAllBooks() {
        return bookRepository.findAll().stream().map(Book::response).collect(Collectors.toList());
    }

    public void removeBook(String bookId) {
        bookRepository.deleteById(bookId);
    }
}
