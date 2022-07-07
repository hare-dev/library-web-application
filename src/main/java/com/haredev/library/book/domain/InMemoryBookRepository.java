package com.haredev.library.book.domain;

import io.vavr.control.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final ConcurrentHashMap<UUID, Book> inMemory = new ConcurrentHashMap<>();

    @Override
    public Book insert(Book book) {
        requireNonNull(book);
        inMemory.put(book.response().getBookId(), book);
        return book;
    }

    @Override
    public Option<Book> findById(UUID bookId) {
        requireNonNull(bookId);
        return Option.of(inMemory.get(bookId));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public void deleteById(UUID bookId) {
        requireNonNull(bookId);
        inMemory.remove(bookId);
    }
}
