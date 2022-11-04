package com.haredev.library.book.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookConfiguration {
    BookFacade bookFacade() {
        return bookFacade(new InMemoryBookRepository(), new InMemoryCategoryRepository());
    }

    @Bean
    BookFacade bookFacade(BookRepository bookRepository, CategoryRepository categoryRepository) {
        BookCreator bookCreator = new BookCreator();
        return new BookFacade(bookRepository, categoryRepository, bookCreator);
    }
}
