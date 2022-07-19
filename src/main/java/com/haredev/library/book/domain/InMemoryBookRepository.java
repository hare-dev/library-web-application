package com.haredev.library.book.domain;

import io.vavr.control.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

class InMemoryBookRepository implements BookRepository {
    private final ConcurrentHashMap<String, Book> inMemory = new ConcurrentHashMap<>();

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
    public void deleteById(String bookId) {
        requireNonNull(bookId);
        inMemory.remove(bookId);
    }

    @Override
    public Option<Book> findById(String bookId) {
        return Option.of(inMemory.get(bookId));
    }
}
