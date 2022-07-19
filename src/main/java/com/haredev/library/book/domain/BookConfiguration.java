package com.haredev.library.book.domain;

import org.apache.commons.validator.routines.ISBNValidator;
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
        ISBNValidator isbnValidator = new ISBNValidator();
        BookValidation bookValidation = new BookValidation(isbnValidator);
        return new BookFacade(bookRepository, bookValidation, bookCreator);
    }
}
