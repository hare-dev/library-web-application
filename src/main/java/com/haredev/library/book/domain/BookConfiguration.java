package com.haredev.library.book.domain;

import com.haredev.library.book.query.BookQueryFacade;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookConfiguration {
    BookFacade bookFacade() {
        return bookFacade(new InMemoryBookRepository(), new InMemoryCommentRepository());
    }

    @Bean
    BookFacade bookFacade(final BookRepository bookRepository, final CommentRepository commentRepository) {
        final BookCreator bookCreator = new BookCreator();
        final CommentCreator commentCreator = new CommentCreator();
        final BookMapper bookMapper = new BookMapper();
        final CommentMapper commentMapper = new CommentMapper();
        final BookManager bookManager = new BookManager(bookRepository, commentRepository,
                bookCreator, commentCreator, bookMapper, commentMapper);
        return new BookFacade(bookManager, bookMapper, commentMapper);
    }

    @Bean
    BookQueryFacade bookSearchParams(final EntityManager entityManager) {
        return new BookQueryFacade(entityManager);
    }
}
