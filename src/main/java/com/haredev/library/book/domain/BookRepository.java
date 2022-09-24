package com.haredev.library.book.domain;

import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

import java.util.List;

interface BookRepository extends Repository<Book, Long> {
    Book save(Book book);
    Option<Book> findById(Long bookId);
    List<Book> findAll();
    void deleteById(Long bookId);
    boolean existsById(Long bookId);
}