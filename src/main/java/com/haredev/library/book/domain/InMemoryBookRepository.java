package com.haredev.library.book.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RequiredArgsConstructor
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
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(inMemory.get(bookId));
    }

    @Override
    public List<Book> findAll(Pageable pageable) {
        return new ArrayList<>(inMemory.values());
    }
}
