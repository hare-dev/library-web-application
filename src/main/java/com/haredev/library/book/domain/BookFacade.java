package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.api.BookError;
import com.haredev.library.book.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
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

    public Option<BookCreateDto> findBook(Long bookId) {
        return bookRepository.findById(bookId).map(Book::response);
    }

    public List<BookCreateDto> fetchAllBooks() {
        return bookRepository.findAll().stream().map(Book::response).collect(Collectors.toList());
    }
    public Either<BookError, BookError> removeBook(Long bookId) {
        if(bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId);
            return Either.right(BookError.OPERATION_SUCCESS); }
        else return Either.left(BookError.BOOK_NOT_FOUND);
    }
}
