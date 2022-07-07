package com.haredev.library.book.domain;

import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

interface BookRepository extends Repository<Book, UUID> {
    Book insert(Book book);
    Option<Book> findById(UUID bookId);
    List<Book> findAll();
    void deleteById(UUID bookId);
}

