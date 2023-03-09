package com.haredev.library.book.domain;

import com.haredev.library.book.query.BookQueryFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
class BookConfiguration {
    BookFacade bookFacade() {
        return bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository());
    }

    @Bean
    BookFacade bookFacade(BookRepository bookRepository, CommentRepository commentRepository) {
        BookCreator bookCreator = new BookCreator();
        CommentCreator commentCreator = new CommentCreator();
        BookManager bookManager = new BookManager(bookRepository, commentRepository, bookCreator, commentCreator);
        return new BookFacade(bookManager);
    }

    @Bean
    BookQueryFacade bookSearchParams(EntityManager entityManager) {
        return new BookQueryFacade(entityManager);
    }
}
