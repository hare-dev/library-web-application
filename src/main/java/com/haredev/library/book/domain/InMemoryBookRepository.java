package com.haredev.library.book.domain;

import io.vavr.control.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final ConcurrentHashMap<Long, Book> inMemory = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        requireNonNull(book);
        inMemory.put(book.response().getBookId(), book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public void deleteById(Long bookId) {
        requireNonNull(bookId);
        inMemory.remove(bookId);
    }

    @Override
    public Option<Book> findById(Long bookId) {
        Book book = inMemory.get(bookId);
        return Option.of(book);
    }

    @Override
    public boolean existsById(Long bookId) {
        return inMemory.contains(bookId);
    }
}
