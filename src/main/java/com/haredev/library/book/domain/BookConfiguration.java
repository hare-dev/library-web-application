package com.haredev.library.book.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookConfiguration {
    BookFacade bookFacade() {
        return bookFacade(new InMemoryBookRepository());
    }

    @Bean
    BookFacade bookFacade(BookRepository bookRepository) {
        BookCreator bookCreator = new BookCreator();
        BookManager bookManager = new BookManager(bookRepository, bookCreator);
        return new BookFacade(bookManager);
    }
}
