package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
import com.haredev.library.book.domain.api.error.BookError;
import com.haredev.library.book.domain.dto.BookCreateDto;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Validation;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.haredev.library.book.domain.api.error.BookError.BOOK_NOT_FOUND;

@RequiredArgsConstructor
public class BookFacade {
    private final BookManager bookManager;

    public Validation<Seq<String>, BookCreateDto> validateBook(BookCreateDto bookCreateDto) {
        return BookValidation.validate(bookCreateDto);
    }

    public BookCreateDto addBook(BookCreateDto bookCreateDto) {
        return bookManager.save(bookCreateDto).response();
    }

    public Either<BookError, BookCreateDto> findBookById(Long bookId) {
        return bookManager.findOne(bookId)
                .map(Book::response)
                .toEither(BOOK_NOT_FOUND);
    }

    public List<BookCreateDto> fetchAllBooks() {

        return bookManager.fetchAll()
                .stream()
                .map(Book::response)
                .collect(Collectors.toList());
    }

    public List<BookCreateDto> fechAllBooksWithPageable(int page, int pageSize) {
        return bookManager.fetchAllWithPageable(page, pageSize)
                .stream()
                .map(Book::response)
                .collect(Collectors.toList());
    }

    public void removeBookById(Long bookId) {
        bookManager.removeOne(bookId);
    }
}