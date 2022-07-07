package com.haredev.library.book.domain;

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
}
