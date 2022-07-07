package com.haredev.library.book.domain;

import com.haredev.library.book.controller.validation.BookValidation;
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
        return new BookFacade(bookRepository, bookCreator);
    }

    @Bean
    BookValidation bookValidation() {
        ISBNValidator isbnValidator = new ISBNValidator();
        return new BookValidation(isbnValidator);
    }
}
