package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import io.vavr.control.Validation;
import lombok.AllArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Transactional
public class BookFacade {
    private final BookRepository bookRepository;
    private final BookCreator bookCreator;

    public BookCreateDto addBook(BookCreateDto request) {
        Book book = bookCreator.from(request);
        return bookRepository.save(book).response();
    }

    public Validation<Seq<String>, BookCreateDto> validateBook(BookCreateDto request) {
        return BookValidation.validate(request);
    }

    public Option<BookCreateDto> findBookById(Long bookId) {
        return Option.ofOptional(bookRepository.findById(bookId).map(Book::response));
    }

    public void removeBookById(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    public List<BookCreateDto> fetchAllBooks() {
        return bookRepository.findAll().stream().map(Book::response).collect(Collectors.toList());
    }
}