package com.haredev.library.book.domain;

import io.vavr.control.Option;
import org.springframework.data.repository.Repository;

import java.util.List;

interface BookRepository extends Repository<Book, String> {
    Book save(Book book);
    Option<Book> findById(String bookId);
    List<Book> findAll();
    void deleteById(String bookId);
}