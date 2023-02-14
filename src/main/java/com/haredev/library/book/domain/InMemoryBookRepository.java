package com.haredev.library.book.domain;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final ConcurrentHashMap<Long, Book> inMemoryBook = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        requireNonNull(book);
        inMemoryBook.put(book.response().getBookId(), book);
        return book;
    }

    @Override
    public void deleteById(Long bookId) {
        requireNonNull(bookId);
        inMemoryBook.remove(bookId);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        requireNonNull(bookId);
        return Optional.of(inMemoryBook.get(bookId));
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return new ArrayList<>(inMemoryBook.values());
    }
}
