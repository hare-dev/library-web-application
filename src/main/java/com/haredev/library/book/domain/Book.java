package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.dto.BookCreateDto;
import com.haredev.library.book.domain.api.BookStatus;
import com.haredev.library.book.domain.api.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
@Builder
@Getter
@AllArgsConstructor
class Book {
        @Id
        private final String bookId;
        private final String title;
        private final String author;
        private final String isbn;
        private final String publisher;
        private final Integer yearPublication;
        private final Integer pageNumber;
        private final String language;
        private final BookType bookType;
        private final BookCover bookCover;
        private final BookStatus bookStatus;
        private final String description;

        BookCreateDto response() {
                return BookCreateDto.builder()
                        .bookId(bookId)
                        .title(title)
                        .author(author)
                        .isbn(isbn)
                        .publisher(publisher)
                        .yearPublication(yearPublication)
                        .pageNumber(pageNumber)
                        .language(language)
                        .bookType(bookType)
                        .bookCover(bookCover)
                        .bookStatus(bookStatus)
                        .description(description)
                        .build();
        }
}
