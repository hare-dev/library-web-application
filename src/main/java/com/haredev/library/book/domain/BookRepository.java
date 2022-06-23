package com.haredev.library.book.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

interface BookRepository extends Repository<UUID, Book> {
    Book save(Book book);
    List<Book> findAll();
    void deleteById(UUID bookId);
    Book findById(UUID bookId);
}
