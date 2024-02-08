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
    public Book save(final Book book) {
        requireNonNull(book);
        inMemoryBook.put(book.response().getId(), book);
        return book;
    }

    @Override
    public void deleteById(final Long bookId) {
        requireNonNull(bookId);
        inMemoryBook.remove(bookId);
    }

    @Override
    public Optional<Book> findById(final Long bookId) {
        requireNonNull(bookId);
        return Optional.ofNullable(inMemoryBook.get(bookId));
    }

    @Override
    public List<Book> findAll(final Pageable pageable) {
        return new ArrayList<>(inMemoryBook.values());
    }
}
