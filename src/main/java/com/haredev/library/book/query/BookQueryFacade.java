package com.haredev.library.book.query;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BookQueryFacade implements BookQueryRepository {
    private final EntityManager entityManager;

    @Override
    public List<BookQueryDto> findAllByCriminalCategory() {
        String query =
                "select new com.haredev.library.book.query.BookQueryDto(b.bookId, b.title, b.author, b.isbn)" +
                        "FROM Book b WHERE b.bookCategory = " +
                        "com.haredev.library.book.domain.api.BookCategory.CRIMINAL";
        return entityManager.createQuery(query, BookQueryDto.class).getResultList();
    }

    @Override
    public List<BookQueryDto> findAllBySoftCover() {
        String query =
                "select new com.haredev.library.book.query.BookQueryDto(b.bookId, b.title, b.author, b.isbn)" +
                        "FROM Book b WHERE b.bookCover = " +
                        "com.haredev.library.book.domain.api.BookCover.soft";
        return entityManager.createQuery(query, BookQueryDto.class).getResultList();
    }
}
