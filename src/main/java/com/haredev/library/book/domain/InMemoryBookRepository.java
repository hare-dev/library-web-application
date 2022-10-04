package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookError;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    public List<Book> findAll(Pageable pageable) {
        return new ArrayList<>(inMemory.values());
    }

    @Override
    public void deleteById(Long bookId) {
        requireNonNull(bookId);
        inMemory.remove(bookId);
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        requireNonNull(bookId);
        return Optional.ofNullable(inMemory.get(bookId));
    }
}
