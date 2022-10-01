package com.haredev.library.book.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

interface BookRepository extends Repository<Book, Long> {
    Book save(Book book);
    Optional<Book> findById(Long bookId);
    List<Book> findAll();
    void deleteById(Long bookId);
}