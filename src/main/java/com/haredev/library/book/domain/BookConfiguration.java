package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
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
        BookValidation bookValidation = new BookValidation();
        return new BookFacade(bookRepository, bookValidation, bookCreator);
    }
}
