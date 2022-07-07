package com.haredev.library.book.domain;

import org.springframework.data.repository.Repository;

import java.util.UUID;

interface BookRepository extends Repository<Book, UUID> {
    Book insert(Book book);
}
