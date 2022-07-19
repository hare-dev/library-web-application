package com.haredev.library.book.domain;

import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookValidation bookValidation;
    private final BookCreator bookCreator;

    public BookCreateDto addBook(BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book).response();
    }

    public Validation<Seq<String>, BookCreateDto> validateBook(BookCreateDto request) {
        return bookValidation.validate(request);
    }

    public Option<BookCreateDto> findBook(String bookId) {
        return bookRepository.findById(bookId).map(Book::response);
    }

    public List<BookCreateDto> fetchAllBooks() {
        return bookRepository.findAll().stream().map(Book::response).collect(Collectors.toList());
    }

    public void removeBook(String bookId) {
        bookRepository.deleteById(bookId);
    }
}
