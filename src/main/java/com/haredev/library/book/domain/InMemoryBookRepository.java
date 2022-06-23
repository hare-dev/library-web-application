package com.haredev.library.book.domain;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final ConcurrentHashMap<UUID, Book> inMemory = new ConcurrentHashMap<>();

    @Override
    public Book save(Book book) {
        requireNonNull(book);
        inMemory.put(book.dto().getBookId(), book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return null;
    }

    @Override
    public void deleteById(UUID bookId) {
        inMemory.remove(bookId);
    }

    @Override
    public Book findById(UUID bookId) {
        return inMemory.get(bookId);
    }
}
