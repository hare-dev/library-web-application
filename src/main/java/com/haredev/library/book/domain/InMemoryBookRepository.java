package com.haredev.library.book.domain;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final HashMap<Long, Book> inMemoryBook = new HashMap<>();

    @Override
    public Book save(final Book book) {
        requireNonNull(book);
        inMemoryBook.put(book.getId(), book);
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
    public List<Book> findAll() {
        return new ArrayList<>(inMemoryBook.values());
    }

    @Override
    public List<Book> findAll(final Pageable pageable) {
        return new ArrayList<>(inMemoryBook.values());
    }
}
