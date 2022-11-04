package com.haredev.library.book.domain;

import com.haredev.library.book.domain.api.BookCover;
import com.haredev.library.book.dto.BookCreateDto;
import com.haredev.library.book.domain.api.BookStatus;
import com.haredev.library.book.domain.api.BookType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "Book")
@Builder
@AllArgsConstructor
@NoArgsConstructor
class Book {
        @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Long bookId;
        private String title;
        private String author;
        private String isbn;
        private String publisher;
        private Integer yearPublication;
        private Integer pageNumber;
        private String language;
        @Enumerated(EnumType.STRING)
        private BookType bookType;
        @Enumerated(EnumType.STRING)
        private BookCover bookCover;
        @Enumerated(EnumType.STRING)
        private BookStatus bookStatus;
        private String description;

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
